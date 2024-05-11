package org.example.springtube.controllers;

import org.example.springtube.dto.ReactionDto;
import org.example.springtube.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReactionController {

    @Autowired
    private ReactionService reactionService;


//    @PostMapping("/reactions")
//    public ResponseEntity<String> addReaction(@RequestBody ReactionDto reactionDto) {
//        try {
//            reactionService.addReaction(reactionDto.getVideoId(), reactionDto.getReactionType());
//            return ResponseEntity.ok("Reaction added successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
}
