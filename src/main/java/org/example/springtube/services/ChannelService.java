package org.example.springtube.services;

import org.example.springtube.dto.ChannelDto;
import org.example.springtube.models.Channel;
import org.springframework.transaction.annotation.Transactional;

public interface ChannelService {
    void createChannel(Channel channel);

 //   long countSubscribers(Long channelId);

    Channel findById(Long channelId);

    @Transactional
    ChannelDto subscribeToChannel(Long channelId);

    @Transactional
    ChannelDto unsubscribeFromChannel(Long channelId);

    boolean isUserSubscribedToChannel(Long channelId, String email);

}
