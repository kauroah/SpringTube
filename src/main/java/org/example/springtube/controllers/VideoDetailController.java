package org.example.springtube.controllers;

import org.example.springtube.dto.ReactionDto;
import org.example.springtube.models.Video;
import org.example.springtube.services.ReactionService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoDetailController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private ReactionService reactionService;


    @GetMapping("/play/{videoId}")
    public String showVideoDetailPage(@PathVariable Long videoId, Model model) {

        // Load the main video
        Video mainVideo = videoService.findById(videoId);
        model.addAttribute("mainVideo", mainVideo);

        List<Video> otherVideos = videoService.getOtherVideos(videoId);
        model.addAttribute("otherVideos", otherVideos);
        model.addAttribute("video", videoId);

        // Fetch reaction stats for each video
        Map<Long, ReactionDto> reactionMap = new HashMap<>();
        for (Video video : otherVideos) {
            reactionMap.put(video.getId(), reactionService.getReactionStatsForVideo(video.getId()));
        }
        model.addAttribute("reactionMap", reactionMap);
        model.addAttribute("oppositeReactionType", "dislike");

        return "video_detail";
    }

    @PostMapping("/reaction")
    public ResponseEntity<String> reactToVideo(@RequestBody Map<String, String> requestBody) {
        Long videoId = Long.valueOf(requestBody.get("videoId"));
        String reactionType = requestBody.get("reactionType");

        reactionService.toggleReaction(videoId, reactionType);
        reactionService.updateReaction(videoId, reactionType);

        return ResponseEntity.ok("Reaction updated successfully");
    }
}