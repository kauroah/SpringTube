package org.example.springtube.models;

import lombok.*;
import org.example.springtube.models.enums.Role;
import org.example.springtube.models.enums.State;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String phone;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Channel channel;


    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> following = new HashSet<>();



    @Enumerated(EnumType.STRING)
    private State state;
    private Role role;
    private String confirmCode;
}
