package org.example.springtube.services;

import org.example.springtube.models.Video;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface VideoService {

//    Mono<Resource> getVideo(String title);
    Video findById(Long id);
  //  String saveFile(MultipartFile uploadFile, Video video);

    String saveFile(MultipartFile uploadFile,  Principal principal);

    void writeFileToResponse(String fileName, HttpServletResponse response);
    Video findByStorageName(String storageName);
    List<Video> getUploadedVideos(Long userId);
}
