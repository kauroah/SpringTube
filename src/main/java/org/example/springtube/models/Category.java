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
/**
 *
 *@Builder: This annotation is from the Lombok library and helps to generate builder methods for creating instances of the class.
 *It provides an alternative way to construct objects with a fluent API-style builder pattern.
 *@AllArgsConstructor: This annotation is from Lombok and generates a constructor with all arguments.
 *It's useful for initializing objects with values for all fields.
 *@NoArgsConstructor: This annotation is from Lombok and generates a constructor with no arguments.
 *It's useful for frameworks like Spring that require default constructors for bean instantiation.
 *@Entity: This annotation marks the class as an entity, indicating that it is a JPA entity
 *and will be mapped to a database table. It's essential for ORM (Object-Relational Mapping)
 *frameworks like Hibernate to recognize this class as a persistent entity.
 *@Data: This annotation is from Lombok and generates getters, setters, equals, hashCode,
 *and toString methods. It helps to reduce boilerplate code by automatically generating these methods.
 *mappedBy attribute indicates that the relationship is defined by the categories
 */