package org.example.springtube.controllers;

import org.example.springtube.models.Video;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class VideoDetailController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/play/{videoId}")
    public String showVideoDetailPage(@PathVariable Long videoId, Model model) {
        // Load the main video
        Video mainVideo = videoService.findById(videoId);
        model.addAttribute("mainVideo", mainVideo);

        // Load other videos (You would need to implement this method in VideoService)
        List<Video> otherVideos = videoService.getOtherVideos(videoId);
        model.addAttribute("otherVideos", otherVideos);

        return "video_detail";
    }
}
