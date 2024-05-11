package org.example.springtube.controllers;

import org.example.springtube.models.Video;
import org.example.springtube.repositories.VideoRepository;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletResponse;


@Controller
public class HomeController {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;


    @GetMapping("/springtube")
    public String home(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "home";
    }


//
//    @GetMapping("/video_detail")
//    public String videoDetailPage() {
//        return "video_detail"; // Return the video_detail.html template
//    }


    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
        Video video = videoService.findByStorageName(fileName);
        if (video == null) {
            // Handle file not found
            return;
        }
        if (fileName.endsWith("_thumbnail")) {
            videoService.writeThumbnailToResponse(video.getThumbnailUrl(), response);
        } else {
            videoService.writeFileToResponse(fileName, response);
        }
    }

//    @GetMapping("/files/{file-name:.+}")
//    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
//        videoService.writeFileToResponse(fileName, response);
//    }

//    @GetMapping("/files/{file_name:.+}")
//    public void getThumbnail(@PathVariable("file_name") String fileName, HttpServletResponse response) {
//        videoService.writeThumbnailToResponse(fileName, response);
//    }
}
