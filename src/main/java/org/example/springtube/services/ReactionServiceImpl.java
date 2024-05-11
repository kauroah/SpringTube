package org.example.springtube.services;

import org.example.springtube.dto.ReactionDto;
import org.example.springtube.models.Reaction;
import org.example.springtube.models.enums.ReactionType;
import org.example.springtube.repositories.ReactionRepository;
import org.example.springtube.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReactionServiceImpl implements ReactionService{

    @Autowired
    private ReactionRepository reactionRepository;


    @Autowired
    private VideoRepository videoRepository;


    @Override
    public ReactionDto getReactionStatsForVideo(Long videoId) {
        int numberOfLikes = reactionRepository.sumLikesForVideo(videoId);
        int numberOfDislikes = reactionRepository.sumDislikesForVideo(videoId);
        return new ReactionDto(numberOfLikes, numberOfDislikes);
    }

//
//    @Override
//    @Transactional
//    public void updateReaction(Long videoId, String reactionType) {
//        // Fetch the existing reaction for the video
//        Reaction reaction = reactionRepository.findByVideoId(videoId);
//
//        // If no reaction exists for the video, create a new one
//        if (reaction == null) {
//            reaction = new Reaction();
//            reaction.setVideo(videoRepository.findById(videoId)
//                    .orElseThrow(() -> new RuntimeException("Video not found")));
//        }
//
//        // Update the reaction based on the reaction type
//        if (reactionType.equals("like")) {
//            reaction.setNumberOfLikes(reaction.getNumberOfLikes() + 1);
//        } else if (reactionType.equals("dislike")) {
//            reaction.setNumberOfDislikes(reaction.getNumberOfDislikes() + 1);
//        } else {
//            throw new IllegalArgumentException("Invalid reaction type");
//        }
//
//        // Save or update the reaction in the database
//        reactionRepository.save(reaction);
//    }



    @Override
    @Transactional
    public void updateReaction(Long videoId, String reactionType) {
        // Fetch the existing reaction for the video
        Reaction reaction = reactionRepository.findByVideoId(videoId);

        // If no reaction exists for the video, create a new one
        if (reaction == null) {
            reaction = new Reaction();
            reaction.setVideo(videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video not found")));
        }

        // Toggle the reaction based on the current reaction type
        if (reactionType.equals("like")) {
            if (reaction.getReactionType() == ReactionType.LIKE) {
                // If the current reaction is already a like, remove the reaction
                reaction.setReactionType(null);
            } else {
                // Otherwise, set the reaction to like
                reaction.setReactionType(ReactionType.LIKE);
            }
        } else if (reactionType.equals("dislike")) {
            if (reaction.getReactionType() == ReactionType.DISLIKE) {
                // If the current reaction is already a dislike, remove the reaction
                reaction.setReactionType(null);
            } else {
                // Otherwise, set the reaction to dislike
                reaction.setReactionType(ReactionType.DISLIKE);
            }
        } else {
            throw new IllegalArgumentException("Invalid reaction type");
        }

        // Save or update the reaction in the database
        reactionRepository.save(reaction);
    }


    @Override
    @Transactional
    public void toggleReaction(Long videoId, String reactionType) {
        // Fetch the existing reaction for the video
        Reaction reaction = reactionRepository.findByVideoId(videoId);

        // If no reaction exists for the video, create a new one
        if (reaction == null) {
            reaction = new Reaction();
            reaction.setVideo(videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video not found")));
        }

        // Toggle the reaction based on the current reaction type
        if (reactionType.equals("like")) {
            if (reaction.getNumberOfLikes() > 0) {
                // If the current reaction is already a like, remove the like
                reaction.setNumberOfLikes(reaction.getNumberOfLikes() - 1);
            } else {
                // Otherwise, add a like
                reaction.setNumberOfLikes(reaction.getNumberOfLikes() + 1);
                // Remove a dislike if it exists
                if (reaction.getNumberOfDislikes() > 0) {
                    reaction.setNumberOfDislikes(reaction.getNumberOfDislikes() - 1);
                }
            }
        } else if (reactionType.equals("dislike")) {
            if (reaction.getNumberOfDislikes() > 0) {
                // If the current reaction is already a dislike, remove the dislike
                reaction.setNumberOfDislikes(reaction.getNumberOfDislikes() - 1);
            } else {
                // Otherwise, add a dislike
                reaction.setNumberOfDislikes(reaction.getNumberOfDislikes() + 1);
                // Remove a like if it exists
                if (reaction.getNumberOfLikes() > 0) {
                    reaction.setNumberOfLikes(reaction.getNumberOfLikes() - 1);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid reaction type");
        }

        // Save or update the reaction in the database
        reactionRepository.save(reaction);
    }


}
