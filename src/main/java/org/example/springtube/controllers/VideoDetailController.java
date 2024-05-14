package org.example.springtube.controllers;

import org.example.springtube.dto.ReactionDto;
import org.example.springtube.models.Video;
import org.example.springtube.security.details.UserDetailsImpl;
import org.example.springtube.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoDetailController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private ReactionService reactionService;
    @Autowired
    private ChannelService channelService;

    @GetMapping("/play/{videoId}")
    public String showVideoDetailPage(@PathVariable Long videoId, Model model) {

        // Load the main video
        Video mainVideo = videoService.findById(videoId);
        model.addAttribute("mainVideo", mainVideo);

        List<Video> otherVideos = videoService.getOtherVideos(videoId);
        model.addAttribute("otherVideos", otherVideos);
    //    model.addAttribute("video", videoId);

        List<Long> otherVideoIds = new ArrayList<>();
        for (Video video : otherVideos) {
            otherVideoIds.add(video.getId());
        }
        model.addAttribute("otherVideoIds", otherVideoIds);


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
    public ResponseEntity<String> reactToVideo(@RequestBody Map<String, String> requestBody, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long videoId = Long.valueOf(requestBody.get("videoId"));
        String reactionType = requestBody.get("reactionType");
        Long userId = userDetails.getUserId(); // Assuming the UserDetails interface has a getUserId() method
        System.out.println("USER ID: " + userId);
        reactionService.toggleReaction(videoId, reactionType, userId);

        reactionService.updateReaction(videoId, reactionType);

        return ResponseEntity.ok("Reaction updated successfully");
    }

    @GetMapping("/checkSubscription/{channelId}")
    @ResponseBody
    public Map<String, Boolean> checkSubscription(@PathVariable Long channelId, Principal principal) {
        boolean subscribed = false;
        if (principal != null) {
            String email = principal.getName(); // Get the username (assuming it's the user's email)
            subscribed = channelService.isUserSubscribedToChannel(channelId, email);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("subscribed", subscribed);
        return response;
    }


    @PostMapping("/subscribe/{channelId}")
    public ResponseEntity<String> subscribeToChannel(@PathVariable Long channelId) {
        channelService.subscribeToChannel(channelId);
        return ResponseEntity.ok("Subscribed successfully");
    }


    @PostMapping("/unsubscribe/{channelId}")
    public ResponseEntity<String> unsubscribeFromChannel(@PathVariable Long channelId) {
        channelService.unsubscribeFromChannel(channelId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

}