package org.example.springtube.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.User;
import org.example.springtube.services.SignUpService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/channel")
    public String showUploadForm(Model model, Principal principal) {
        try {
            User user = signUpService.getCurrentUserByEmail(principal.getName());
            List<VideoDto> userVideos = videoService.getUploadedVideos(user.getId());
            model.addAttribute("videos", userVideos);
            model.addAttribute("channelName", user.getChannel().getName());
            log.info("Displayed upload form for user: {}", user.getEmail());
            return "upload";
        } catch (Exception e) {
            log.error("Error while displaying upload form: {}", e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/channel")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("thumbnail") MultipartFile thumbnail,
                                   @RequestParam Set<String> categories,
                                   Principal principal,
                                   Model model) {
        try {
            if (file.isEmpty() || thumbnail.isEmpty()) {
                throw new IllegalArgumentException("Please select both video and thumbnail files.");
            }
            videoService.saveFile(file, thumbnail, categories, principal);
            log.info("Video uploaded successfully by user: {}", principal.getName());
            return "redirect:/channel";
        } catch (Exception e) {
            log.error("Error while uploading files: {}", e.getMessage());
            model.addAttribute("errorMessage", "An unexpected error occurred while uploading the files. Please try again later.");
            return "redirect:/error";
        }
    }

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        try {
            videoService.writeFileToResponse(fileName, response);
            log.info("Retrieved file: {}", fileName);
        } catch (Exception e) {
            log.error("Error while retrieving file {}: {}", fileName, e.getMessage());
        }
    }

    @GetMapping("/thumbnails/{file-name:.+}")
    public void getThumbnail(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        try {
            videoService.writeThumbnailToResponse(fileName, response);
            log.info("Retrieved thumbnail: {}", fileName);
        } catch (Exception e) {
            log.error("Error while retrieving thumbnail {}: {}", fileName, e.getMessage());
        }
    }
}
