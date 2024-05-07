package org.example.springtube.services;

import org.example.springtube.models.Comment;
import org.example.springtube.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CommentServiceImpl implements CommentService {
//    private final CommentRepository commentRepository;
//
//    @Autowired
//    public CommentServiceImpl(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }
//
//    public void updateComment(Long commentId, String newContent) {
//        Optional<Comment> optionalComment = commentRepository.findById(commentId);
//        if (optionalComment.isPresent()) {
//            Comment comment = optionalComment.get();
//            comment.setContent(newContent);
//            commentRepository.save(comment);
//        } else {
//            System.out.println("Comment not found" +commentId);
//        }
//    }
}
