package org.example.springtube.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.UserDto;
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
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    // Injecting the UserService, ChannelService, and VideoService beans
    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoService videoService;

    // ADMIN ACTIONS TO USERS

    /**
     * Displays the admin dashboard with a list of all users.
     *
     * @param model the model to add attributes to
     * @return the name of the view to render
     */
//    @GetMapping("/dashboard")
//    public String dashboard(Model model) {
//        model.addAttribute("users", userService.findAllUsers());
//        return "adminDashboard";
//    }
    /**
     *
     * @param model
     * @param page
     * @param size
     * @param query
     * @param sortParameter
     * @param directionParameter
     * @return return the dashboard with paginated users
     */
    @GetMapping("/dashboard")
    public String showUsers(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String query,
                            @RequestParam(defaultValue = "id") String sortParameter,
                            @RequestParam(defaultValue = "ASC") String directionParameter) {
        try {
            List<UserDto> users = userService.search(page, size, query, sortParameter, directionParameter);
            model.addAttribute("users", users);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", users.size() / size);  // Update this based on actual total pages from service
            return "adminDashboard";
        } catch (Exception e) {
            log.info("An unexpected error occurred while fetching users", e);
            model.addAttribute("errorMessage", "An unexpected error occurred while fetching users. Please try again later.");
            return "redirect:/error";
        }
    }

    /**
     * Blocks a user by their ID.
     *
     * @param userId the ID of the user to block
     * @return a redirect to the admin dashboard
     */
    @PostMapping("/block")
    public String blockUser(@RequestParam Long userId) {
        userService.blockUser(userId);
        return "redirect:/admin/dashboard";
    }

    /**
     * Unblocks a user by their ID.
     *
     * @param userId the ID of the user to unblock
     * @return a redirect to the admin dashboard
     */
    @PostMapping("/unblock")
    public String unblockUser(@RequestParam Long userId) {
        userService.unblockUser(userId);
        return "redirect:/admin/dashboard";
    }

    /**
     * Grants admin role to a user by their ID.
     *
     * @param userId the ID of the user to grant admin role
     * @return a redirect to the admin dashboard
     */
    @PostMapping("/grantAdmin")
    public String grantAdminRole(@RequestParam Long userId) {
        userService.grantAdminRole(userId);
        return "redirect:/admin/dashboard";
    }

    /**
     * Revokes admin role from a user by their ID.
     *
     * @param userId the ID of the user to revoke admin role
     * @return a redirect to the admin dashboard
     */
    @PostMapping("/revokeAdmin")
    public String revokeAdminRole(@RequestParam Long userId) {
        userService.revokeAdminRole(userId);
        return "redirect:/admin/dashboard";
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     * @return a redirect to the admin dashboard
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/dashboard";
    }

    // ADMIN ACTIONS TO CHANNELS

    /**
     * Displays a list of all channels.
     *
     * @param model the model to add attributes to
     * @return the name of the view to render
     */
    @GetMapping("/channels")
    public String showChannels(Model model) {
        List<Channel> channels = channelService.findAll();
        model.addAttribute("channels", channels);
        return "adminChannels";
    }

    /**
     * Updates a channel's name by its ID.
     *
     * @param channelId the ID of the channel to update
     * @param newName   the new name for the channel
     * @return a redirect to the channels view
     */
    @PostMapping("/channels/update")
    public String updateChannel(@RequestParam Long channelId, @RequestParam String newName) {
        channelService.updateChannel(channelId, newName);
        return "redirect:/admin/channels";
    }

    // ADMIN ACTIONS TO VIDEOS

    /**
     * Displays a list of all videos.
     *
     * @param model the model to add attributes to
     * @return the name of the view to render
     */
    @GetMapping("/videos")
    public String showVideos(Model model) {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "adminVideos";
    }

    /**
     * Deletes a video by its ID along with its reactions.
     *
     * @param videoId the ID of the video to delete
     * @return a redirect to the videos view
     */
    @PostMapping("/videos/delete")
    public String deleteVideo(@RequestParam Long videoId) {
        videoService.deleteReactionsByVideoId(videoId);
        videoService.deleteVideo(videoId);
        return "redirect:/admin/videos";
    }

}