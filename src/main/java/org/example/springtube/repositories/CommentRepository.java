package org.example.springtube.repositories;

import org.example.springtube.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByVideoId(Long videoId);

}
