package org.example.springtube.repositories;

import org.example.springtube.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Finds a user by their ID, returning an Optional<User>
    Optional<User> findById(Long id);

    // Finds a user by their email, returning an Optional<User>
    Optional<User> findByEmail(String email);

    // Finds a user by their confirmation code, returning an Optional<User>
    Optional<User> findByConfirmCode(String confirmCode);

    // Custom update query to update the user's profile details
    // @Transactional ensures that the query is executed within a transaction
    // @Modifying indicates that this query performs an update operation
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.phone = :phone, u.password = :password WHERE u.email = :email")
    void updateUserProfile(@Param("email") String email,
                           @Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("phone") String phone,
                           @Param("password") String password);

    // Finds a user by their userId, returning a User
    User findUserById(Long userId);




    @Query("SELECT u FROM User u WHERE " +
            "(:q = 'empty' OR UPPER(u.firstName) LIKE UPPER(CONCAT('%', :q, '%')) OR " +
            "UPPER(u.lastName) LIKE UPPER(CONCAT('%', :q, '%')) OR " +
            "UPPER(u.email) LIKE UPPER(CONCAT('%', :q, '%')) OR " +
            "UPPER(u.phone) LIKE UPPER(CONCAT('%', :q, '%'))) ")
    Page<User> search(@Param("q") String q, Pageable pageable);




}


/**
 * @Transactional annotation ensures that the method executes within a transactional context.
 * This means that the entire method's execution is considered as a single transaction
 * Using this annotation, the method will be executed within a transaction, and any changes made
 * to the database will be committed automatically.
 * * and if any fails nothing will be commited to the database
 *
 * @Query annotation is used to indicate that the annotated method performs a query operation
 * (such as a select or join query), rather than an update or delete operation.
 *
 * @Modifying annotation is used to indicate that the annotated method performs a modifying query
 * (such as an update or delete operation), rather than a select query.
 */