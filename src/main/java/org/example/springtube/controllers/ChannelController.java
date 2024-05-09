package org.example.springtube.controllers;

import org.example.springtube.models.Channel;
import org.example.springtube.models.User;
import org.example.springtube.repositories.UserRepository;
import org.example.springtube.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;
import java.util.Optional;

@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/createChannel")
    public String showChannelForm(Model model, Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getChannel() != null) {
                return "redirect:/channel";
            }
        }
        model.addAttribute("channel", new Channel());
        return "createchannel";
    }

    @PostMapping("/createChannel")
    public String createChannel(@ModelAttribute("channel") Channel channel, Principal principal) {
        String email = principal.getName();
        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            channel.setUser(user);
            channelService.createChannel(channel);
            return "redirect:/springtube";
        } else {
            return "redirect:/error";
        }
    }
}