package org.example.springtube.services;

import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.Video;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface VideoService {

    Video findById(Long id);
    String saveFile(MultipartFile uploadFile, MultipartFile thumbnailFile, Principal principal);
    void writeFileToResponse(String fileName, HttpServletResponse response);
    Video findByStorageName(String storageName);
    List<Video> getUploadedVideos(Long userId);
    void writeThumbnailToResponse(String fileName, HttpServletResponse response);
    List<Video> getOtherVideos(Long mainVideoId);
    Video findByThumbnail(String thumbnailUrl);

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
