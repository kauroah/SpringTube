package org.example.springtube.services;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.converters.StringToLocalDateTimeConverterService;
import org.example.springtube.dto.CommentDto;
import org.example.springtube.models.Comment;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;
import org.example.springtube.repositories.CommentRepository;
import org.example.springtube.repositories.UserRepository;
import org.example.springtube.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private StringToLocalDateTimeConverterService stringToLocalDateTimeConverterService;

    /**
     * Saves a comment to the repository.
     *
     * @param comment the comment to save.
     * @return the saved comment.
     */
    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Retrieves comments by video ID and maps them to CommentDto objects.
     *
     * @param videoId the ID of the video.
     * @return a list of CommentDto objects.
     */
    @Override
    public List<CommentDto> getCommentsByVideoId(Long videoId) {
        List<Comment> comments = commentRepository.findByVideoId(videoId);
        return comments.stream().map(CommentDto::from).collect(Collectors.toList());
    }

//    /**
//     * Updates an existing comment.
//     *
//     * @param comment the comment to update.
//     * @return the updated comment.
//     * @throws RuntimeException if the comment does not exist.
//     */
//    @Override
//    public Comment updateComment(Comment comment) {
//        Optional<Comment> existingCommentOptional = commentRepository.findById(comment.getId());
//        if (existingCommentOptional.isPresent()) {
//            return commentRepository.save(comment);
//        } else {
//            throw new RuntimeException("Comment not found with ID: " + comment.getId());
//        }
//    }

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to delete.
     * @return
     * @throws RuntimeException if the comment does not exist.
     */
    @Override
    public CommentDto deleteComment(Long commentId) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isPresent()) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("Comment not found with ID: " + commentId);
        }
        return null;
    }

//
//    @Override
//    public CommentDto deleteComment(Long commentId) {
//        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
//        if (existingCommentOptional.isPresent()) {
//            Comment existingComment = existingCommentOptional.get();
//            commentRepository.deleteById(commentId);
//            return CommentDto.from(existingComment); // Convert the deleted comment to CommentDto
//        } else {
//            throw new RuntimeException("Comment not found with ID: " + commentId);
//        }
//    }

    /**
     * Retrieves comments for a specific video by its ID.
     *
     * @param videoId the ID of the video.
     * @return a list of comments for the specified video.
     */
    @Override
    public List<Comment> getCommentsForVideo(Long videoId) {
        return commentRepository.findByVideoId(videoId);
    }

    /**
     * Creates a new comment and saves it to the repository.
     *
     * @param userId the ID of the user creating the comment.
     * @param videoId the ID of the video being commented on.
     * @param text the text of the comment.
     * @return the created CommentDto.
     */
    @Override
    @Transactional
    public CommentDto createComment(Long userId, Long videoId, String text) {
        // Retrieve the user and video entities from their respective repositories
        User user = userRepository.findUserById(userId);
        Video video = videoRepository.findVideoById(videoId);

        // Get the current date and time as a formatted string
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Convert the formatted string to a LocalDateTime object
        LocalDateTime commentDate = stringToLocalDateTimeConverterService.convert(currentDateTime);

        // Create a new comment object and set its properties
        Comment comment = Comment.builder()
                .user(user)
                .video(video)
                .text(text)
                .commentDate(String.valueOf(commentDate))
                .build();

        // Save the comment to the repository
        saveComment(comment);

        // Convert the saved comment to a CommentDto and return it
        return CommentDto.from(comment);
    }

    @Override
    public CommentDto findById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.map(CommentDto::from).orElse(null);
    }

    @Override
    public CommentDto updateComment(Long commentId, String newText) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isPresent()) {
            Comment existingComment = existingCommentOptional.get();
            existingComment.setText(newText);
            commentRepository.save(existingComment);
            return CommentDto.from(existingComment);
        } else {
            throw new RuntimeException("Comment not found with ID: " + commentId);
        }
    }

}