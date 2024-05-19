package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springtube.models.Channel;
import org.example.springtube.models.Video;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoDto {
    private Long id; // ID of the video
    private String originalName; // Original name of the video file
    private String storageFileName; // Storage file name of the video
    private String url; // URL of the video
    private Long size; // Size of the video
    private String type;
    private String thumbnailStorageName; // Type of the video
    private String thumbnailUrl; // URL of the video's thumbnail
    private Channel channel; // Channel associated with the video
    private Set<CommentDto> comments; // Set of comments associated with the video

    /**
     * Static factory method to create a VideoDto object from a Video entity.
     * @param video The Video entity from which to create the VideoDto.
     * @return The created VideoDto object.
     */
    public static VideoDto from(Video video) {
        return VideoDto.builder()
                .id(video.getId()) // Set the ID of the video
                .originalName(video.getOriginalName()) // Set the original name of the video
                .storageFileName(video.getStorageFileName()) // Set the storage file name of the video
                .url(video.getUrl()) // Set the URL of the video
                .size(video.getSize()) // Set the size of the video
                .type(video.getType()) // Set the type of the video
                .thumbnailUrl(video.getThumbnailUrl())
                .thumbnailStorageName(video.getThumbnailStorageName())// Set the URL of the video's thumbnail
                .channel(video.getChannel()) // Set the channel associated with the video
                .comments(video.getComments().stream() // Map each Comment entity to CommentDto using CommentDto.from method
                        .map(CommentDto::from)
                        .collect(Collectors.toSet())) // Collect the CommentDto objects into a set
                .build(); // Build the VideoDto object
    }
}
