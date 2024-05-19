package org.example.springtube.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Data
@Getter
@Setter
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
    private String thumbnailStorageName;


    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;


    @OneToMany(mappedBy = "video")
    private List<Comment> comments;



    @ManyToMany
    @JoinTable(
            name = "video_categories",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


}