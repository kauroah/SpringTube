package org.example.springtube.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.UserDto;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.example.springtube.security.details.UserDetailsImpl;
import org.example.springtube.services.UserService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
@Slf4j
@Controller
public class HomeController {

    // Injecting the VideoService and UserService beans
    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    /**
     * Displays the home page with a list of videos and the current user details.
     *
     * @param model        the model to add attributes to
     * @param query        the search query for filtering videos (optional)
     * @param userDetails  the currently authenticated user
     * @return the name of the view to render the home page
     */
    @GetMapping("/springtube")
    public String home(Model model, @RequestParam(required = false) String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Retrieve the current user and convert to a UserDto
            User user = userService.findUserById(userDetails.getUserId());
            UserDto userDto = UserDto.from(user);
            model.addAttribute("user", userDto);

            List<Video> videos;
            // If a query is provided, search for videos matching the query
            if (query != null && !query.isEmpty()) {
                videos = videoService.searchVideos(query);
            } else {
                // Otherwise, retrieve all videos
                videos = videoService.findAll();
            }

            // Shuffle the list of videos
            Collections.shuffle(videos);
            model.addAttribute("videos", videos);

            // If no videos are found, redirect to YouTube with the search query
            if (videos.isEmpty() && query != null && !query.isEmpty()) {
                String youtubeSearchUrl = "https://www.youtube.com/results?search_query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
                return "redirect:" + youtubeSearchUrl;
            }

            log.info("Home page loaded successfully for user {}", userDetails.getUsername());
            return "home";
        } catch (Exception e) {
            // If an unexpected error occurs, log the error and redirect to the error page
            log.error("An unexpected error occurred while loading the home page", e);
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the home page. Please try again later.");
            return "redirect:/error";
        }
    }



//    /**
//     * Retrieves a file (video or thumbnail) and writes it to the response.
//     *
//     * @param fileName the name of the file to retrieve
//     * @param response the HTTP response to write the file to
//     */
//    @GetMapping("/files/{file-name:.+}")
//    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
//        videoService.writeFileToResponse(fileName, response);
//        // Uncomment the following lines if you need to handle thumbnails and videos differently
//        // videoService.writeThumbnailToResponse(fileName, response);
//        // videoService.writeFileAndThumbnailToResponse(fileName, response);
//    }

//    /**
//     * Retrieves a thumbnail and writes it to the response.
//     *
//     * @param thumbnailName the name of the thumbnail to retrieve
//     * @param response      the HTTP response to write the thumbnail to
//     */


    // Uncomment and adjust the following methods if needed for additional file handling
    // @GetMapping("/files/{file-thumbnail:.+}")
    // public void getThumbnail(@PathVariable("file-thumbnail") String fileName, HttpServletResponse response) {
    //     videoService.writeThumbnailToResponse(fileName, response);
    // }

    // @GetMapping("/files/{file_name:.+}")
    // public void getThumbnail(@PathVariable("file_name") String fileName, HttpServletResponse response) {
    //     videoService.writeThumbnailToResponse(fileName, response);
    // }
}