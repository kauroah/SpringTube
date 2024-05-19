package org.example.springtube.services;

import org.example.springtube.dto.ChannelDto;
import org.example.springtube.models.Channel;

import java.util.List;

public interface ChannelService {

    ChannelDto findChannelById(Long channelId);

    void createChannel(Channel channel);

    Channel findById(Long channelId);

    ChannelDto subscribeToChannel(Long channelId, Long userId);

    ChannelDto unsubscribeToChannel(Long channelId, Long userId);

    boolean isUserSubscribedToChannel(Long channelId, String email);

    List<Channel> findAll();

    void updateChannel(Long channelId, String newName);

    boolean createChannelForUser(Channel channel, String email);

    boolean userCanCreateChannel(String email);

    int getFollowerCount(Long channelId);
}
