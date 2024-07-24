package org.example.springtube.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.Category;
import org.example.springtube.models.Channel;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.example.springtube.repositories.CategoryRepository;
import org.example.springtube.repositories.ReactionRepository;
import org.example.springtube.repositories.UserRepository;
import org.example.springtube.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Value("${storage.path.thumbnails}")
    private String storageThumbnails;

    private static final Logger logger = Logger.getLogger(VideoServiceImpl.class.getName());


    @Override
    public VideoDto findById(Long id) {
        return VideoDto.from(videoRepository.findById(id).get());
    }

    /**
     * This method saves a new video along with its thumbnail.
     * first it creates directories if they don't exist
     * then it saves the files to the disk
     * then it saves the video and thumbnail to the database
     * @param uploadFile
     * @param thumbnailFile
     * @param categoryNames
     * @param principal
     * @return
     */
    @Override
    public String saveFile(MultipartFile uploadFile, MultipartFile thumbnailFile, Set<String> categoryNames, Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Channel channel = user.getChannel();
            if (channel != null) {

                // Check for duplicate video name
                if (videoRepository.findByOriginalName(uploadFile.getOriginalFilename()) != null) {
                    throw new IllegalStateException("Video with the same name already exists.");
                }

                // Check for empty video or thumbnail file
                if (uploadFile.isEmpty() || thumbnailFile.isEmpty()) {
                    throw new IllegalArgumentException("Both video and thumbnail files are required.");
                }

                // Extract the file extensions of the uploaded video and thumbnail files
                String videoExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
                String thumbnailExtension = FilenameUtils.getExtension(thumbnailFile.getOriginalFilename());

                String videoStorageName = UUID.randomUUID().toString() + "." + videoExtension;
                String thumbnailStorageName = UUID.randomUUID().toString() + "." + thumbnailExtension;

                try {
                    // Define paths for video and thumbnail
                    String videoFullPath = Paths.get("src/main/resources", storagePath,videoStorageName).toString();
                    String thumbnailFullPath = Paths.get("src/main/resources", storageThumbnails,thumbnailStorageName).toString();

                    // Create directories if they don't exist
                    Files.createDirectories(Paths.get(videoFullPath).getParent());
                    Files.createDirectories(Paths.get(thumbnailFullPath).getParent());

                    // Save files
                    Files.copy(uploadFile.getInputStream(), Paths.get(videoFullPath));
                    Files.copy(thumbnailFile.getInputStream(), Paths.get(thumbnailFullPath));
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error occurred while saving files.", e);
                    throw new IllegalStateException("Failed to save files", e);
                }

                // Check for missing categories
                if (categoryNames == null || categoryNames.isEmpty()) {
                    throw new IllegalArgumentException("At least one category is required.");
                }

                // Find or create categories
                Set<Category> categories = new HashSet<>();
                for (String name : categoryNames) {
                    Category category = categoryRepository.findByName(name);
                    if (category == null) {
                        category = new Category();
                        category.setName(name);
                        category = categoryRepository.save(category);
                    }
                    categories.add(category);
                }

                // Build Video object and save to repository
                Video video = Video.builder()
                        .type(uploadFile.getContentType())
                        .originalName(uploadFile.getOriginalFilename())
                        .size(uploadFile.getSize())
                        .storageFileName(videoStorageName)
                        .url(storagePath + "/" + videoStorageName)
                        .thumbnailUrl(storageThumbnails + "/" + thumbnailStorageName)
                        .thumbnailStorageName(thumbnailStorageName)// Set the thumbnail URL
                        .channel(channel)
                        .categories(categories) // Assign categories to the video
                        .build();

                videoRepository.save(video);

                return video.getStorageFileName();
            }
        }
        throw new IllegalStateException("Failed to save files: User's channel not found");
    }







    @Override
    public void writeThumbnailToResponse(String fileName, HttpServletResponse response) {
        Video thumbnailVideo = videoRepository.findByThumbnailStorageName(fileName);
        if (thumbnailVideo == null) {
            throw new IllegalArgumentException("Thumbnail file not found with name: " + fileName);
        }
        response.setContentType(thumbnailVideo.getType());
        try {
            // Read the thumbnail image from file and write it to the response output stream
            IOUtils.copy(new FileInputStream("src/main/resources" + "/" + thumbnailVideo.getThumbnailUrl()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while writing thumbnail.", e);
        }
    }


    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        Video fileInfo = videoRepository.findByStorageFileName(fileName);
        if (fileInfo == null) {
            throw new IllegalArgumentException("File not found with name: " + fileName);
        }
        response.setContentType(fileInfo.getType());
        try {
            IOUtils.copy(new FileInputStream("src/main/resources" + "/" + fileInfo.getUrl()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while adding user.", e);
        }
    }

    @Override
    public Video findByStorageName(String storageName) {
        return videoRepository.findByStorageFileName(storageName);
    }

    @Override
    public List<VideoDto> getUploadedVideos(Long userId) {
        return videoRepository.findByUserId(userId);
    }



    @Override
    public List<VideoDto> getOtherVideos(Long mainVideoId) {
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

    @Override
    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
    }

    @Override
    @Transactional
    public void deleteVideosByChannelId(Long channelId) {
        videoRepository.deleteByChannelId(channelId);
    }

    @Override
    @Transactional
    public void deleteReactionsByVideoId(Long videoId) {
        reactionRepository.deleteByVideoId(videoId);
    }

    @Override
    public List<VideoDto> findVideosByChannelId(Long channelId) {
        return videoRepository.findByChannelId(channelId);
    }
}