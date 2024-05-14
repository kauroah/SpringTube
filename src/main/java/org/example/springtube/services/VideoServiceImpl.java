package org.example.springtube.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.example.springtube.models.Channel;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.example.springtube.repositories.UserRepository;
import org.example.springtube.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Override
    public Video findById(Long id) {
        return videoRepository.findById(id).get();
    }


    @Override
    public String saveFile(MultipartFile uploadFile, MultipartFile thumbnailFile, Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Channel channel = user.getChannel();
            if (channel != null) {
                String videoExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
                String thumbnailExtension = FilenameUtils.getExtension(thumbnailFile.getOriginalFilename());

                String videoStorageName = UUID.randomUUID().toString() + "." + videoExtension;
                String thumbnailStorageName = UUID.randomUUID().toString() + "_thumbnail." + thumbnailExtension;

                try {
                    String videoFullPath = Paths.get("src/main/resources", storagePath, videoStorageName).toString();
                    String thumbnailFullPath = Paths.get("src/main/resources", storagePath, thumbnailStorageName).toString();

                    Files.createDirectories(Paths.get(videoFullPath).getParent()); // Create directories if they don't exist
                    Files.createDirectories(Paths.get(thumbnailFullPath).getParent()); // Create directories if they don't exist

                    Files.copy(uploadFile.getInputStream(), Paths.get(videoFullPath));
                    Files.copy(thumbnailFile.getInputStream(), Paths.get(thumbnailFullPath));
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to save files", e);
                }

                Video video = Video.builder()
                        .type(uploadFile.getContentType())
                        .originalName(uploadFile.getOriginalFilename())
                        .size(uploadFile.getSize())
                        .storageFileName(videoStorageName)
                        .url(storagePath + "/" + videoStorageName)
                        .thumbnailUrl(storagePath + "/" + thumbnailStorageName) // Set the thumbnail URL
                        .channel(channel)
                        .build();

                videoRepository.save(video);

                return video.getStorageFileName();
            }
        }
        throw new IllegalStateException("Failed to save files: User's channel not found");
    }


    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        Video fileInfo = videoRepository.findByStorageFileName(fileName);
        if (fileInfo == null) {
            throw new IllegalArgumentException("File not found with name: " + fileName);
        }
        response.setContentType(fileInfo.getType());
        try {
            IOUtils.copy(new FileInputStream("src/main/resources"+"/"+fileInfo.getUrl()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Video findByStorageName(String storageName) {
        return videoRepository.findByStorageFileName(storageName);
    }

    @Override
    public List<Video> getUploadedVideos(Long userId) {
        return videoRepository.findByUserId(userId);
    }

    @Override
    public void writeThumbnailToResponse(String thumbnailUrl, HttpServletResponse response) {
        Video thumbnailVideo = videoRepository.findByThumbnailUrl(thumbnailUrl);
        if (thumbnailVideo == null) {
            throw new IllegalArgumentException("Thumbnail file not found with name: " + thumbnailUrl);
        }
        response.setContentType("image/png"); // Assuming thumbnails are JPEG images
        try {
            // Read the thumbnail image from file and write it to the response output stream
            IOUtils.copy(new FileInputStream("src/main/resources" + "/" + thumbnailVideo.getThumbnailUrl()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error writing thumbnail to response", e);
        }
    }




    @Override
    public List<Video> getOtherVideos(Long mainVideoId) {
        return videoRepository.findAllExceptId(mainVideoId);
    }

    @Override
    public Video findByThumbnail(String thumbnailUrl) {
        return videoRepository.findByThumbnailUrl(thumbnailUrl);
    }

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> searchVideos(String query) {
        if (query == null || query.isEmpty()) {
            return videoRepository.findAll();
        } else {
            return videoRepository.findByOriginalNameContainingIgnoreCase(query); // Adjust the method name according to your repository
        }
    }

//    @Override
//    public List<Video> findByTitleContainingIgnoreCase(String query) {
//        return videoRepository.findByTitleContainingIgnoreCase(query);
//    }
}