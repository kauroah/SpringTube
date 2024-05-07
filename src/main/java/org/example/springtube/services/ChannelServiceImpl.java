package org.example.springtube.services;

import org.example.springtube.models.Channel;
import org.example.springtube.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Override
    public void createChannel(Channel channel) {
        channelRepository.save(channel);

    }
}
