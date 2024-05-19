package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springtube.models.Channel;
import org.example.springtube.models.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDto {

    private Long id; // ID of the channel
    private String name; // Name of the channel
    private User user; // User associated with the channel
    private Integer subscriberCount; // Number of subscribers of the channel

    /**
     * Static factory method to create a ChannelDto object from a Channel entity.
     * @param channel The Channel entity from which to create the ChannelDto.
     * @return The created ChannelDto object.
     */
    public static ChannelDto from(Channel channel) {
        return ChannelDto.builder()
                .id(channel.getId()) // Set the ID of the channel
                .name(channel.getName()) // Set the name of the channel
                .user(channel.getUser()) // Set the user associated with the channel
                .subscriberCount(channel.getFollowers().size()) // Set the number of subscribers of the channel
                .build(); // Build the ChannelDto object
    }
}
