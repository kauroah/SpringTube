package org.example.springtube.services;

import org.example.springtube.dto.ReactionDto;
import org.example.springtube.models.Reaction;
import org.example.springtube.models.Video;
import org.example.springtube.models.enums.ReactionType;
import org.example.springtube.repositories.ReactionRepository;
import org.example.springtube.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;


    @Override
    public ReactionDto getReactionStatsForVideo(Long videoId) {
        int numberOfLikes = reactionRepository.sumLikesForVideo(videoId);
        int numberOfDislikes = reactionRepository.sumDislikesForVideo(videoId);
        return new ReactionDto(numberOfLikes, numberOfDislikes);
    }

    @Override
    @Transactional
    public void toggleReaction(Long videoId, String reactionType, Long userId) {
        // Fetch the existing reaction for the video and user
        Reaction reaction = reactionRepository.findByVideoIdAndUserId(videoId, userId);

        // If no reaction exists for the video and user, create a new one
        if (reaction == null) {
            reaction = new Reaction();
            reaction.setVideo(videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video not found")));
            reaction.setUserId(userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")).getId());
        }

        // Update the reaction based on the reaction type
        if (reactionType.equals("like")) {
            if (reaction.getNumberOfLikes() > 0) {
                reaction.setNumberOfLikes(0); // Remove like
            } else {
                reaction.setNumberOfLikes(1); // Add like
                // Decrease dislikes if user liked after disliking
                if (reaction.getNumberOfDislikes() > 0) {
                    reaction.setNumberOfDislikes(0);
                }
            }
        } else if (reactionType.equals("dislike")) {
            if (reaction.getNumberOfDislikes() > 0) {
                reaction.setNumberOfDislikes(0); // Remove dislike
            } else {
                reaction.setNumberOfDislikes(1); // Add dislike
                // Decrease likes if user disliked after liking
                if (reaction.getNumberOfLikes() > 0) {
                    reaction.setNumberOfLikes(0);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid reaction type");
        }

        // Save or update the reaction in the database
        reactionRepository.save(reaction);
    }




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


}
