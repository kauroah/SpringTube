package org.example.springtube.services;

import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface VideoService {

    VideoDto findById(Long id);
    String saveFile(MultipartFile uploadFile, MultipartFile thumbnailFile, Set<String> categoryNames, Principal principal);
    void writeFileToResponse(String fileName, HttpServletResponse response);
    Video findByStorageName(String storageName);
    List<VideoDto> getUploadedVideos(Long userId);
    void writeThumbnailToResponse(String fileName, HttpServletResponse response);
    List<VideoDto> getOtherVideos(Long mainVideoId);
    Video findByThumbnail(String thumbnailUrl);
    List<Video> findAll();

    List<Video> searchVideos(String query);

    void deleteVideo(Long videoId);

    @Transactional
    void deleteVideosByChannelId(Long channelId);

    void deleteReactionsByVideoId(Long videoId);

    List<VideoDto> findVideosByChannelId(Long channelId);

}