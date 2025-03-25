package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


/**
 * Repository interface for {@link User} entity.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for advanced query capabilities.
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to search for.
     * @return An {@link Optional} containing the user if found, or empty if no user is found with the given username.
     */
    public Optional<User> findByUsername(String username);
}
