package org.example.springtube.services;

import org.example.springtube.dto.ChannelDto;
import org.example.springtube.models.Channel;
import org.example.springtube.models.User;
import org.example.springtube.repositories.ChannelRepository;
import org.example.springtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;


    @Autowired
    private UserRepository userRepository;



    @Override
    public void createChannel(Channel channel) {
        channelRepository.save(channel);

    }

    @Override
    public Channel findById(Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
    }

    @Override
    public Channel findChannelByUser(String email, Long channelId) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Channel> optionalChannel = channelRepository.findById(channelId);
            if (optionalChannel.isPresent()) {
                Channel channel = optionalChannel.get();
                if (user.getSubscribedChannels().contains(channel)) {
                    return channel;
                }

            }
        }
        return null;
    }


    @Override
    @Transactional
    public ChannelDto subscribeToChannel(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid channel id: " + channelId));
        channel.subscribe();
        Channel savedChannel = channelRepository.save(channel);
        return mapToDto(savedChannel);
    }


    @Override
    @Transactional
    public ChannelDto unsubscribeFromChannel(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid channel id: " + channelId));
        channel.unsubscribe();
        Channel savedChannel = channelRepository.save(channel);
        return mapToDto(savedChannel);
    }




    @Override
    public boolean isUserSubscribedToChannel(Long channelId, String email) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Optional<Channel> optionalChannel = channelRepository.findById(channelId);
                if (optionalChannel.isPresent()) {
                    Channel channel = optionalChannel.get();
                    return user.getSubscribedChannels().contains(channel);
                }
            }
            return false;
        }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteChannel(Long channelId) {
        System.out.println("11111111111111111111111111111111111");
        channelRepository.deleteChannelById(channelId);
        System.out.println("22222222222222222222222222222222222222222");
    }

    @Override
    public void updateChannel(Long channelId, String newName) {
        Optional<Channel> optionalChannel = channelRepository.findById(channelId);
        if (optionalChannel.isPresent()) {
            Channel channel = optionalChannel.get();
            channel.setName(newName);
            channelRepository.save(channel);
        }
    }

    // Helper method to map Channel entity to ChannelDTO
    private ChannelDto mapToDto(Channel channel) {
        return ChannelDto.builder()
                .id(channel.getId())
                .name(channel.getName())
                .subscriberCount(channel.getSubscriberCount())
                .build();
    }
}
