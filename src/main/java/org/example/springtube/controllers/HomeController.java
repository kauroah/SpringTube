package org.example.springtube.controllers;
import org.example.springtube.dto.UserDto;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.example.springtube.security.details.UserDetailsImpl;
import org.example.springtube.services.UserService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;


    @GetMapping("/springtube")
    public String home(Model model, @RequestParam(required = false) String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findUserById(userDetails.getUserId());
        UserDto userDto = UserDto.from(user);
        model.addAttribute("user", userDto);
        List<Video> videos = videoService.searchVideos(query);
        model.addAttribute("videos", videoService.findAll());

        if (videos.isEmpty()) {
            String youtubeSearchUrl = "https://www.youtube.com/results?search_query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
            return "redirect:" + youtubeSearchUrl;
        }
        model.addAttribute("videos", videos);

        return "home";
    }




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
