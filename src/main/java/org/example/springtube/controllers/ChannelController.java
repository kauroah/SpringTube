package org.example.springtube.controllers;

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
        // Retrieve channel details
        ChannelDto channel = channelService.findChannelById(channelId);
        // Retrieve videos associated with the channel
        List<VideoDto> videos = videoService.findVideosByChannelId(channelId);
        // Shuffle the list of videos
        Collections.shuffle(videos);
        // Add channel and videos to the model
        model.addAttribute("channel", channel);
        model.addAttribute("videos", videos);

        return "viewChannel";
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
        String email = principal.getName();
        // Check if the user can create a channel
        if (!channelService.userCanCreateChannel(email)) {
            return "redirect:/channel";
        }
        // Add a new Channel object to the model
        model.addAttribute("channel", new Channel());
        return "createchannel";
    }

    /**
     * Handles the submission of the form to create a new channel.
     *
     * @param channel   the channel data from the form
     * @param principal the currently authenticated user
     * @return a redirect to the appropriate page based on success or failure
     */
    @PostMapping("/createChannel")
    public String createChannel(@ModelAttribute("channel") Channel channel, Principal principal) {
        String email = principal.getName();
        // Attempt to create the channel for the user
        if (channelService.createChannelForUser(channel, email)) {
            return "redirect:/springtube";
        } else {
            return "redirect:/error";
        }
    }
}
