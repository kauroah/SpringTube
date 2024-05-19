package org.example.springtube.repositories;

import org.example.springtube.models.Reaction;
import org.example.springtube.models.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
//    int countByVideoIdAndType(Long videoId, String name);
    @Query("SELECT COALESCE(SUM(r.numberOfLikes), 0) FROM Reaction r WHERE r.video.id = ?1")
    int sumLikesForVideo(Long videoId);
    @Query("SELECT COALESCE(SUM(r.numberOfDislikes), 0) FROM Reaction r WHERE r.video.id = ?1")
    int sumDislikesForVideo(Long videoId);

    Reaction findByVideoId(Long videoId);

    Reaction findByVideoIdAndUserId(Long videoId, Long userId);

    void deleteByVideoId(Long videoId);
}


/**
 * Purpose:
 * This query calculates the total number of likes for a specific video.
 * Explanation:
 * SELECT COALESCE(SUM(r.numberOfLikes), 0):
 * This part of the query sums up the numberOfLikes column from the Reaction table for a given video ID.
 * COALESCE(SUM(r.numberOfLikes), 0): The COALESCE function returns the sum if it is not null. If there are no likes (i.e., the sum is null), it returns 0 instead.
 * FROM Reaction r WHERE r.video.id = ?1: This part specifies that the sum should be calculated for reactions associated with the given video ID (?1 is a placeholder for the method parameter videoId).
 */