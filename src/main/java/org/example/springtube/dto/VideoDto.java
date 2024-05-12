package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springtube.models.Channel;
import org.example.springtube.models.User;
import org.example.springtube.models.Video;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoDto {
    private Long id;
    private String originalName;
    private String storageFileName;
    private String url;
    private Long size;
    private String type;
    private String thumbnailUrl;
    private Channel channel;
    private Set<CommentDto> comments;

    public static VideoDto from(Video video) {
        return VideoDto.builder()
                .id(video.getId())
                .originalName(video.getOriginalName())
                .storageFileName(video.getStorageFileName())
                .url(video.getUrl())
                .size(video.getSize())
                .type(video.getType())
                .thumbnailUrl(video.getThumbnailUrl())
                .channel(video.getChannel())
                .comments(video.getComments().stream()
                        .map(CommentDto::from)
                        .collect(Collectors.toSet()))
                .build();
    }
}