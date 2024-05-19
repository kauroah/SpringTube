package org.example.springtube.services;

import lombok.extern.slf4j.Slf4j;
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
import java.util.logging.Level;
import java.util.logging.Logger;
@Slf4j
@Component
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(ChannelServiceImpl.class.getName());

    /**
     * Finds a channel by its ID and converts it to a ChannelDto.
     *
     * @param channelId the ID of the channel to find.
     * @return the ChannelDto representation of the channel.
     */
    @Override
    public ChannelDto findChannelById(Long channelId) {
        return ChannelDto.from(channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found")));
    }

    /**
     * Creates a new channel by saving it to the repository.
     *
     * @param channel the channel to create.
     */
    @Override
    public void createChannel(Channel channel) {
        channelRepository.save(channel);
    }

    /**
     * Finds a channel by its ID.
     *
     * @param channelId the ID of the channel to find.
     * @return the Channel entity.
     */
    @Override
    public Channel findById(Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
    }

    /**
     * Finds a channel associated with a user by their email and channel ID.
     *
     * @param email the email of the user.
     * @param channelId the ID of the channel.
     * @return the Channel entity if found and the user is subscribed; otherwise, null.
     */
//    @Override
//    public Channel findChannelByUser(String email, Long channelId) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            Optional<Channel> optionalChannel = channelRepository.findById(channelId);
//            if (optionalChannel.isPresent()) {
//                Channel channel = optionalChannel.get();
//                if (user.getSubscribedChannels().contains(channel)) {
//                    return channel;
//                }
//            }
//        }
//        return null;
//    }

    /**
     * Subscribes a user to a channel.
     *
     * @param channelId the ID of the channel.
     * @param userId the ID of the user.
     * @return the ChannelDto representation of the subscribed channel.
     */
    @Override
    public ChannelDto subscribeToChannel(Long channelId, Long userId) {
        User user = userRepository.getOne(userId);
        Channel channel = channelRepository.getOne(channelId);
        if (!user.getSubscribedChannels().contains(channel)) {
            user.getSubscribedChannels().add(channel);
            userRepository.save(user);
        }
        return mapToDto(channel);
    }

    /**
     * Unsubscribes a user from a channel.
     *
     * @param channelId the ID of the channel.
     * @param userId the ID of the user.
     * @return the ChannelDto representation of the unsubscribed channel.
     */
    @Override
    public ChannelDto unsubscribeToChannel(Long channelId, Long userId) {
        User user = userRepository.getOne(userId);
        Channel channel = channelRepository.getOne(channelId);
        if (user.getSubscribedChannels().contains(channel)) {
            user.getSubscribedChannels().remove(channel);
            userRepository.save(user);
        }
        return mapToDto(channel);
    }

    /**
     * Checks if a user is subscribed to a channel.
     *
     * @param channelId the ID of the channel.
     * @param email the email of the user.
     * @return true if the user is subscribed, false otherwise.
     */
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

    /**
     * Retrieves all channels.
     *
     * @return a list of all Channel entities.
     */
    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    /**
     * Updates the name of a channel.
     *
     * @param channelId the ID of the channel to update.
     * @param newName the new name for the channel.
     */
    @Override
    public void updateChannel(Long channelId, String newName) {
        Optional<Channel> optionalChannel = channelRepository.findById(channelId);
        if (optionalChannel.isPresent()) {
            Channel channel = optionalChannel.get();
            channel.setName(newName);
            channelRepository.save(channel);
        }
    }

    /**
     * Maps a Channel entity to a ChannelDto.
     *Converts a Channel entity to a ChannelDto.
     * @param channel the Channel entity to map.
     * @return the ChannelDto representation.
     */
    private ChannelDto mapToDto(Channel channel) {
        return ChannelDto.builder()
                .id(channel.getId())
                .name(channel.getName())
                .subscriberCount(channel.getSubscriberCount())
                .build();
    }

    /**
     * Creates a channel for a user.
     *
     * @param channel the channel to create.
     * @param email the email of the user.
     * @return true if the channel is created successfully, false otherwise.
     */
    @Override
    public boolean createChannelForUser(Channel channel, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            channel.setUser(user);
            channelRepository.save(channel);
            return true;
        }
        return false;
    }

    /**
     * Checks if a user can create a channel.
     *
     * @param email the email of the user.
     * @return true if the user can create a channel, false otherwise.
     */
    @Override
    public boolean userCanCreateChannel(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.isPresent() && userOpt.get().getChannel() == null;
    }

    /**
     * Gets the number of followers for a channel.
     *
     * @param channelId the ID of the channel.
     * @return the number of followers.
     */
    @Override
    public int getFollowerCount(Long channelId) {
        Optional<Channel> channel = channelRepository.findById(channelId);
        return channel.map(c -> c.getFollowers().size()).orElse(0);
    }
}