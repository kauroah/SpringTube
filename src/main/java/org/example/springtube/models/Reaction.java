package org.example.springtube.models;

import lombok.*;
import org.example.springtube.models.enums.ReactionType;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfLikes;

    private int numberOfDislikes;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;


    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
