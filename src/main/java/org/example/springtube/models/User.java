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
            name = "user_follows_channel",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Set<Channel> subscribedChannels = new HashSet<>();


    @Enumerated(EnumType.STRING)
    private State state;
//
    @Enumerated(EnumType.STRING)
    private Role role;


    private String confirmCode;
}
