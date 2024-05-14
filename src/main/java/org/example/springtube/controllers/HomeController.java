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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private VideoService videoService;


//    @GetMapping("/springtube")
//    public String home(Model model) {
//        model.addAttribute("videos", videoService.findAll());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAuthenticated = auth.isAuthenticated();
//        model.addAttribute("isAuthenticated", isAuthenticated);
//
//
//        if (isAuthenticated) {
//            String email = auth.getName();
//            model.addAttribute("userId", email);
//            System.out.println("USER Email: " + email);
//        }
//
//        return "home";
//    }


    @GetMapping("/springtube")
    public String home(Model model, @RequestParam(required = false) String query) {
        List<Video> videos = videoService.searchVideos(query);
        model.addAttribute("videos", videos);

        return "home";
    }


//
//    @GetMapping("/video_detail")
//    public String videoDetailPage() {
//        return "video_detail"; // Return the video_detail.html template
//    }


//    @GetMapping("/files/{file-name:.+}")
//    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
//        Video video = videoService.findByStorageName(fileName);
//        if (video == null) {
//            return;
//        }
//        if (fileName.endsWith("_thumbnail")) {
//            videoService.writeThumbnailToResponse(video.getThumbnailUrl(), response);
//        } else {
//            videoService.writeFileToResponse(fileName, response);
//        }
//    }

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
        videoService.writeFileToResponse(fileName, response);
       // videoService.writeThumbnailToResponse(fileName, response);
      //  videoService.writeFileAndThumbnailToResponse(fileName, response);
    }
//
    @GetMapping("/thumbnails/{thumbnail-name:.+}")
    public void getThumbnail(@PathVariable("thumbnail-name") String thumbnailName, HttpServletResponse response){
        videoService.writeThumbnailToResponse(thumbnailName, response);
    }



//    @GetMapping("/files/{file-thumbnail:.+}")
//    public void getThumbnail(@PathVariable("file-thumbnail") String fileName, HttpServletResponse response) {
//        videoService.writeThumbnailToResponse(fileName, response);
//    }

//    @GetMapping("/files/{file_name:.+}")
//    public void getThumbnail(@PathVariable("file_name") String fileName, HttpServletResponse response) {
//        videoService.writeThumbnailToResponse(fileName, response);
//    }
}
