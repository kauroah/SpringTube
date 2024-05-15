package org.example.springtube.services;

import org.example.springtube.models.Video;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface VideoService {

    Video findById(Long id);
    String saveFile(MultipartFile uploadFile, MultipartFile thumbnailFile, Set<String> categoryNames, Principal principal);
    void writeFileToResponse(String fileName, HttpServletResponse response);
    Video findByStorageName(String storageName);
    List<Video> getUploadedVideos(Long userId);
    void writeThumbnailToResponse(String fileName, HttpServletResponse response);
    List<Video> getOtherVideos(Long mainVideoId);
    Video findByThumbnail(String thumbnailUrl);
    List<Video> findAll();

    List<Video> searchVideos(String query);

    void deleteVideo(Long videoId);

    void deleteVideosByChannelId(Long channelId);

    void deleteReactionsByVideoId(Long videoId);

    List<Video> findVideosByChannelId(Long channelId);


    //     List<Video> findByTitleContainingIgnoreCase(String query);

    //  void writeFileAndThumbnailToResponse(String fileName, HttpServletResponse response);

//    VideoDto likeVideo(Long userId, Long videoId);
//
//    VideoDto dislikeVideo(Long userId, Long videoId);
//
//
//
//    void likeVideo(Long videoId);
//
//    void dislikeVideo(Long videoId);

    //  ResponseEntity<List<Video>> getAllOtherVideos();

}
