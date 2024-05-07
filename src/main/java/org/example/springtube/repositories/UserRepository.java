package org.example.springtube.repositories;

import org.example.springtube.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    // You can add custom query methods here if needed
    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmCode(String confirmCode);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.phone = :phone, u.password = :password WHERE u.id = :id")
    void updateUser(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone, @Param("password") String password);
}

