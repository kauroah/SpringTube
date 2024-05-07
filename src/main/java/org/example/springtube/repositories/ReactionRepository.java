package org.example.springtube.repositories;

import org.example.springtube.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    // You can add custom query methods here if needed
}
