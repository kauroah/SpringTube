package org.example.springtube.repositories;

import org.example.springtube.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // Method to save a new channel
    Channel save(Channel channel);

    // Method to find a channel by its ID
    Optional<Channel> findById(Long id);

    // Method to find all channels
    List<Channel> findAll();
    @Transactional
    @Modifying
    @Query("update Channel c set c.name = :name where c.id = :id")
    void updateChannel(@Param("id") Long id, @Param("name") String name);

    // Method to delete a channel by its ID
    void deleteChannelById(Long channelId);
}
