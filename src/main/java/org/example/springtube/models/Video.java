package org.example.springtube.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String storageFileName;
    private String url;
    private Long size;
    private String type;
    private String thumbnailUrl;


    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;


    @OneToMany(mappedBy = "video")
    private Set<Comment> comments = new HashSet<>();
}