package org.example.springtube.repositories;

import org.example.springtube.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // You can add custom query methods here if needed
    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    Comment save(Comment comment);

    void deleteById(Long id);

//    void updateComment(Long id, String content);
}
