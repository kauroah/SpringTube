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
}
