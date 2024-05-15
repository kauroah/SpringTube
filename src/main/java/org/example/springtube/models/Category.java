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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Video> videos = new HashSet<>();
}
