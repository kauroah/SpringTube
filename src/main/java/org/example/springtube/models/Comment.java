package org.example.springtube.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
