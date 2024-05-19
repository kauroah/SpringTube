package org.example.springtube.repositories;


import org.example.springtube.dto.VideoDto;
import org.example.springtube.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByStorageFileName (String fileName);
    Video findByThumbnailStorageName(String fileName);


    @Query("SELECT v FROM Video v WHERE v.id <> :videoId ORDER BY RAND()")
    List<VideoDto> findAllExceptId(@Param("videoId") Long videoId);


    Video findByThumbnailUrl(String thumbnailUrl);

    @Query("SELECT v FROM Video v WHERE v.channel.user.id = :userId")
    List<VideoDto> findByUserId(@Param("userId") Long userId);

    List<Video> findByOriginalNameContainingIgnoreCase(String query);

    void deleteByChannelId(Long channelId);

    List<VideoDto> findByChannelId(Long channelId);

    Video findVideoById(Long videoId);


    Video findByOriginalName(String originalName);
}