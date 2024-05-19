package org.example.springtube.controllers;

import org.example.springtube.dto.*;
import org.example.springtube.models.*;
import org.example.springtube.security.details.UserDetailsImpl;
import org.example.springtube.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class VideoDetailController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private ReactionService reactionService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private CommentService commentService;

    /**
     * Displays the detailed view of a video including its reactions, comments, and related videos.
     *
     * @param videoId The ID of the video to display details for.
     * @param model   The model to pass attributes to the view.
     * @return The name of the view template for the video detail page.
     */
    @GetMapping("/play/{videoId}")
    public String showVideoDetailPage(@PathVariable Long videoId, Model model) {
        // Load the main video
        VideoDto mainVideo = videoService.findById(videoId);
        model.addAttribute("mainVideo", mainVideo);

        // Load the channel details for the main video
        ChannelDto channelDto = ChannelDto.from(mainVideo.getChannel());
        model.addAttribute("channelDto", channelDto);

        // Retrieve and add follower count for the channel
        int followerCount = channelService.getFollowerCount(mainVideo.getChannel().getId());
        model.addAttribute("followerCount", followerCount);

        // Load and add other videos related to the main video
        List<VideoDto> otherVideos = videoService.getOtherVideos(videoId);
        model.addAttribute("otherVideos", otherVideos);
        model.addAttribute("video", videoId);

        // Add IDs of other videos for client-side processing
        List<Long> otherVideoIds = new ArrayList<>();
        for (VideoDto video : otherVideos) {
            otherVideoIds.add(video.getId());
        }
        model.addAttribute("otherVideoIds", otherVideoIds);

        // Load and add reaction statistics for other videos
        Map<Long, ReactionDto> reactionMap = new HashMap<>();
        for (VideoDto video : otherVideos) {
            reactionMap.put(video.getId(), reactionService.getReactionStatsForVideo(video.getId()));
        }
        model.addAttribute("reactionMap", reactionMap);
        model.addAttribute("oppositeReactionType", "dislike");

        // Load and add comments for the main video
        List<Comment> comments = commentService.getCommentsForVideo(videoId);
        model.addAttribute("comments", comments);

        return "video_detail";
    }

    /**
     * Handles the user's reaction to a video (like/dislike).
     *
     * @param requestBody   JSON request body containing video ID and reaction type.
     * @param userDetails   The details of the authenticated user.
     * @return ResponseEntity indicating success or failure of the reaction update.
     */
    @PostMapping("/reaction")
    public ResponseEntity<String> reactToVideo(@RequestBody Map<String, String> requestBody,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long videoId = Long.valueOf(requestBody.get("videoId"));
        String reactionType = requestBody.get("reactionType");
        Long userId = userDetails.getUserId();

        // Toggle the user's reaction to the video and update reaction statistics
        reactionService.toggleReaction(videoId, reactionType, userId);
        reactionService.updateReaction(videoId, reactionType);

        return ResponseEntity.ok("Reaction updated successfully");
    }

    /**
     * Checks if the current user is subscribed to a channel.
     *
     * @param channelId  The ID of the channel to check subscription status for.
     * @param principal  The security principal representing the current user.
     * @return A map containing the subscription status (true/false).
     */
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

    /**
     * Subscribes the current user to a channel.
     *
     * @param channelId    The ID of the channel to subscribe to.
     * @param userDetails  The details of the authenticated user.
     * @return ResponseEntity containing the updated subscriber count.
     */
    @PostMapping("/subscribe/{channelId}")
    public ResponseEntity<String> subscribeToChannel(@PathVariable Long channelId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getUserId();
            ChannelDto channelDto = channelService.subscribeToChannel(channelId, userId);
            if (channelDto == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, Integer> response = new HashMap<>();
            response.put("subscriberCount", channelDto.getSubscriberCount());
            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            // Consider logging the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while subscribing to the channel.");
        }
    }

    /**
     * Unsubscribes the current user from a channel.
     *
     * @param channelId    The ID of the channel to unsubscribe from.
     * @param userDetails  The details of the authenticated user.
     * @return ResponseEntity containing the updated subscriber count.
     */
    @PostMapping("/unsubscribe/{channelId}")
    public ResponseEntity<Map<String, Integer>> unsubscribeFromChannel(@PathVariable Long channelId,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        ChannelDto channelDto = channelService.unsubscribeToChannel(channelId, userId);
        Map<String, Integer> response = new HashMap<>();
        response.put("subscriberCount", channelDto.getSubscriberCount());
        return ResponseEntity.ok(response);
    }

    /**
     * Saves or updates a comment for a video.
     *
     * @param userDetails  The details of the authenticated user.
     * @param videoId      The ID of the video to comment on.
     * @param text         The text content of the comment.
     * @return ResponseEntity containing the saved comment DTO.
     */
    @PostMapping("/comment")
    @ResponseBody
    public ResponseEntity<CommentDto> saveOrUpdateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @RequestParam("videoId") Long videoId,
                                                          @RequestParam("text") String text) {
        Long userId = userDetails.getUserId();
        CommentDto commentDto = commentService.createComment(userId, videoId, text);
        return ResponseEntity.ok(commentDto);
    }

}