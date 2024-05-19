package org.example.springtube.controllers;

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

@Controller
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private SignUpService signUpService;

    /**
     * Displays the video upload form with a list of user's uploaded videos.
     *
     * @param model     The model to pass attributes to the view.
     * @param principal The security principal of the currently logged-in user.
     * @return A string representing the view to be rendered.
     */
    @GetMapping("/channel")
    public String showUploadForm(Model model, Principal principal) {
        try {
            User user = signUpService.getCurrentUserByEmail(principal.getName());
            List<VideoDto> userVideos = videoService.getUploadedVideos(user.getId());
            model.addAttribute("videos", userVideos);
            model.addAttribute("channelName", user.getChannel().getName());
            return "upload";
        } catch (Exception e) {
            return "redirect:/error"; // Redirect to a generic error page if there is an exception
        }
    }

    /**
     * Handles the upload of a video file and its thumbnail.
     *
     * @param file       The video file to upload.
     * @param thumbnail  The thumbnail for the video.
     * @param categories The categories associated with the video.
     * @param principal  The security principal of the currently logged-in user.
     * @return A redirect string to refresh the page.
     */
    @PostMapping("/channel")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("thumbnail") MultipartFile thumbnail,
                                   @RequestParam Set<String> categories,
                                   Principal principal) {
        if (file.isEmpty() || thumbnail.isEmpty()) {
            return String.valueOf(ResponseEntity.badRequest().body("Please select both video and thumbnail files."));
        }
        videoService.saveFile(file, thumbnail, categories, principal);

        return "redirect:/channel";
    }

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        videoService.writeFileToResponse(fileName, response);
    }


    @GetMapping("/thumbnails/{file-name:.+}")
    public void getThumbnail(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        videoService.writeThumbnailToResponse(fileName, response);
    }
}



