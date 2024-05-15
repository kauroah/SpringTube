package org.example.springtube.controllers;

import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.example.springtube.services.SignUpService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/channel")
    public String showUploadForm(Model model, Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = Optional.ofNullable(signUpService.getUserByUsername(email));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Video> userVideos = videoService.getUploadedVideos(user.getId());
            VideoDto videoDto = VideoDto.from(userVideos.get(0));
            model.addAttribute("video", videoDto);
            videoService.findVideosByChannelId(user.getId());
            model.addAttribute("videos", userVideos); // Add a new Video object to the model
            model.addAttribute("channelName", user.getChannel().getName()); // Add channel name
            return "upload"; // Returns the upload form HTML page
        }
        return "redirect:/error";
    }


    @PostMapping("/channel")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("thumbnail") MultipartFile thumbnail,
                                   Principal principal) {

        if (file.isEmpty() || thumbnail.isEmpty()) {
            return String.valueOf(ResponseEntity.badRequest().body("Please select both video and thumbnail files."));
        }

        videoService.saveFile(file, thumbnail, principal);

        return "redirect:/channel";
    }
}
