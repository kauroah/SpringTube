package org.example.springtube.services;

import org.example.springtube.dto.ChannelDto;
import org.example.springtube.models.Channel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChannelService {
    void createChannel(Channel channel);
    Channel findById(Long channelId);
    Channel findChannelByUser(String email, Long channelId);
    @Transactional
    ChannelDto subscribeToChannel(Long channelId);
    @Transactional
    ChannelDto unsubscribeFromChannel(Long channelId);
    boolean isUserSubscribedToChannel(Long channelId, String email);
    List<Channel> findAll();
    void deleteChannel(Long channelId);

    void updateChannel(Long channelId, String newName);
}
