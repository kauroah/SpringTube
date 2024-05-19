package org.example.springtube.services;

import org.example.springtube.dto.ReactionDto;
import org.example.springtube.models.enums.ReactionType;
import org.springframework.transaction.annotation.Transactional;

public interface ReactionService{

    ReactionDto getReactionStatsForVideo(Long videoId);

    @Transactional
    void toggleReaction(Long videoId, String reactionType, Long userId);

    @Transactional
    void updateReaction(Long videoId, String reactionType);

    // @Transactional
    //   void toggleReaction(Long videoId, String reactionType);
}