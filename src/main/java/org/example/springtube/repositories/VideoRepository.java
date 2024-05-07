package org.example.springtube.repositories;


import org.example.springtube.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    // You can add custom query methods here if needed
    Video findByStorageFileName (String fileName);

    List<Video> findByUploaderId(Long userId);
}
