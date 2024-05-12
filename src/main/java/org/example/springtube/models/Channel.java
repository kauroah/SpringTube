package org.example.springtube.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "channel")
    private Set<Video> videos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_follows_channel",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> followers = new HashSet<>();

//    @Column(name = "subscriber_count", nullable = false)
    private Integer subscriberCount = 0;


    public void subscribe() {
        this.subscriberCount++;
    }

    public void unsubscribe() {
        if (this.subscriberCount > 0) {
            this.subscriberCount--;
        }
    }
}




