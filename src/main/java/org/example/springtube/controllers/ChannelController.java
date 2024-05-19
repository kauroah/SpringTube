package org.example.springtube.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.ChannelDto;
import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.Channel;
import org.example.springtube.services.ChannelService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
public class ChannelController {

    // Injecting the ChannelService and VideoService beans
    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoService videoService;

    /**
     * Displays a specific channel and its videos.
     *
     * @param channelId the ID of the channel to view
     * @param model     the model to add attributes to
     * @return the name of the view to render
     */
    @GetMapping("/channel/{channelId}")
    public String viewChannel(@PathVariable Long channelId, Model model) {
        try {
            // Retrieve channel details
            ChannelDto channel = channelService.findChannelById(channelId);
            // Retrieve videos associated with the channel
            List<VideoDto> videos = videoService.findVideosByChannelId(channelId);
            // Shuffle the list of videos
            Collections.shuffle(videos);
            // Add channel and videos to the model
            model.addAttribute("channel", channel);
            model.addAttribute("videos", videos);

            log.info("Viewing channel with ID: {}", channelId);
            return "viewChannel";
        } catch (Exception e) {
            log.info("An unexpected error occurred while viewing channel with ID: {}", channelId, e);
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "redirect:/error";
        }
    }

    /**
     * Displays the form for creating a new channel.
     *
     * @param model     the model to add attributes to
     * @param principal the currently authenticated user
     * @return the name of the view to render
     */
    @GetMapping("/createChannel")
    public String showChannelForm(Model model, Principal principal) {
        try {
            String email = principal.getName();
            if (!channelService.userCanCreateChannel(email)) {
                return "redirect:/channel";
            }
            model.addAttribute("channel", new Channel());
            log.info("Showing channel creation form for user: {}", email);
            return "createchannel";
        } catch (Exception e) {
            log.info("An unexpected error occurred while showing channel creation form", e);
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "redirect:/error";
        }
    }

    /**
     * Handles the submission of the form to create a new channel.
     *
     * @param channel   the channel data from the form
     * @param principal the currently authenticated user
     * @return a redirect to the appropriate page based on success or failure
     */
    @PostMapping("/createChannel")
    public String createChannel(@ModelAttribute("channel") Channel channel, Principal principal, Model model) {
        try {
            String email = principal.getName();
            if (channelService.createChannelForUser(channel, email)) {
                log.info("Channel created successfully for user: {}", email);
                return "redirect:/springtube";
            } else {
                log.info("Failed to create channel for user: {}", email);
                model.addAttribute("errorMessage", "Failed to create channel.");
                return "redirect:/error";
            }
        } catch (Exception e) {
            log.info("An unexpected error occurred while creating channel", e);
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "redirect:/error";
        }
    }

}
