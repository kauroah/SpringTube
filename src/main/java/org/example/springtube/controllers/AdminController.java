package org.example.springtube.controllers;

import org.example.springtube.models.Channel;
import org.example.springtube.models.Video;
import org.example.springtube.services.ChannelService;
import org.example.springtube.services.UserService;
import org.example.springtube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoService videoService;



//    ADMIN ACTIONS TO USERS

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "adminDashboard";
    }

    @PostMapping("/block")
    public String blockUser(@RequestParam Long userId) {
        userService.blockUser(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/unblock")
    public String unblockUser(@RequestParam Long userId) {
        userService.unblockUser(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/grantAdmin")
    public String grantAdminRole(@RequestParam Long userId) {
        userService.grantAdminRole(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/revokeAdmin")
    public String revokeAdminRole(@RequestParam Long userId) {
        userService.revokeAdminRole(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/dashboard";
    }


//    ADMIN ACTIONS TO CHANNELS

@GetMapping("/channels")
public String showChannels(Model model) {
    List<Channel> channels = channelService.findAll();
    model.addAttribute("channels", channels);
    return "adminChannels";
}

    @PostMapping("/channels/update")
    public String updateChannel(@RequestParam Long channelId, @RequestParam String newName) {
        channelService.updateChannel(channelId, newName);
        return "redirect:/admin/channels";
    }


//    ADMIN ACTIONS TO VIDEOS
@GetMapping("/videos")
public String showVideos(Model model) {
    List<Video> videos = videoService.findAll();
    model.addAttribute("videos", videos);
    return "adminVideos";
}

    @PostMapping("/videos/delete")
    public String deleteVideo(@RequestParam Long videoId) {
        videoService.deleteReactionsByVideoId(videoId);
        videoService.deleteVideo(videoId);
        return "redirect:/admin/videos";
    }
}
