package org.example.springtube.services;

import org.example.springtube.dto.CommentDto;
import org.example.springtube.models.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    Comment saveComment(Comment comment);

    List<CommentDto> getCommentsByVideoId(Long videoId);

    // boolean saveComment(CommentDto commentDto);

    Comment updateComment(Comment comment);

    void deleteComment(Long commentId);

    List<Comment> getAllComments();

    Comment getCommentById(Long commentId);

    List<Comment> getCommentsForVideo(Long videoId);

    @Transactional
    CommentDto createComment(Long userId, Long videoId, String text);
}