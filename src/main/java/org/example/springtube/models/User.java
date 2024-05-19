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
//@Data
@Getter
@Setter
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


    @OneToOne(mappedBy = "user")
    private Channel channel;

    @ManyToMany
    @JoinTable(
            name = "user_follows_channel",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id",referencedColumnName = "id")
    )
    private Set<Channel> subscribedChannels = new HashSet<>();

    // Specifies that the 'role' field is mapped to a database column of type enum
    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Role role;


    private String confirmCode;
}