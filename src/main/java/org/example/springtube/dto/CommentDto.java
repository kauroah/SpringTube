package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springtube.models.Comment;
import org.example.springtube.repositories.PasswordResetTokenRepository;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentDto {
    private Long id;
    private Long userId;
    private String userName; // Add userName field
    private Long videoId;
    private String text;
    private String commentDate;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setUserName(comment.getUser().getFirstName());
        commentDto.setVideoId(comment.getVideo().getId());
        commentDto.setText(comment.getText());
        commentDto.setCommentDate(comment.getCommentDate());
        return commentDto;
    }
}