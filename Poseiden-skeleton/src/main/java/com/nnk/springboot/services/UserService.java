package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dto.auth.PasswordChangeDTO;
import com.nnk.springboot.dto.auth.RegisterDTO;
import com.nnk.springboot.exceptions.services.EntityNotFoundException;
import com.nnk.springboot.exceptions.services.EntityUpdateFailException;
import com.nnk.springboot.exceptions.user.*;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.utils.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer responsible for handling user-related operations.
 */
@Service
public class UserService extends EntityService<User, UserDTO, Integer> implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidatorService validatorService;

    private static final String DEFAULT_ROLE = "USER";

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return this.userRepository;
    }

    @Override
    protected Integer getEntityID(User user) {
        return user.getId();
    }

    @Override
    protected void setEntityId(User user, Integer integer) {
        user.setId(integer);
    }

    @Override
    protected UserDTO mapEntity(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullname(user.getFullname());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        return dto;
    }

    @Override
    protected User mapFromDTO(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }

    @Override
    protected String getEntityName() {
        return User.class.getName();
    }

    /**
     * Finds a user by username.
     *
     * @param username The username to search for.
     * @return The corresponding UserDTO.
     * @throws EntityNotFoundException if the user is not found.
     */
    public UserDTO findByUsername(String username) throws EntityNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
        return this.mapEntity(user);
    }

    /**
     * Registers a new user.
     *
     * @param registerDTO The data to register a new user.
     * @return True if registration is successful, false otherwise.
     * @throws PasswordsAreNotEqualsException if passwords do not match.
     * @throws UsernameAlreadyExistsException if the username already exists.
     */
    public boolean registerUser(RegisterDTO registerDTO) throws PasswordsAreNotEqualsException, UsernameAlreadyExistsException {
        if(!validatorService.passwordAreSame(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
            throw new PasswordsAreNotEqualsException();
        }

        // Check if username already exists
        try {
            this.findByUsername(registerDTO.getUsername());
        }
        catch(Exception ex) {
            throw new UsernameAlreadyExistsException(registerDTO.getUsername());
        }

        User user = new User();

        user.setUsername(registerDTO.getUsername());
        user.setFullname(registerDTO.getFullName());
        user.setPassword(registerDTO.getPassword());
        user.setRole(DEFAULT_ROLE);

        try {
            this.saveEntity(user);
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }

    /**
     * Creates a new user.
     *
     * @param userDTO The user data to create a new user.
     * @return True if user is created successfully, false otherwise.
     * @throws UsernameAlreadyExistsException if the username already exists.
     */
    public boolean createUser(UserDTO userDTO) throws UsernameAlreadyExistsException {
        // Check if username already exists
        try {
            this.findByUsername(userDTO.getUsername());
        }
        catch(Exception ex) {
            throw new UsernameAlreadyExistsException(userDTO.getUsername());
        }

        try {
            this.saveEntity( this.mapFromDTO(userDTO) );
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }

    /**
     * Updates an existing user.
     *
     * @param id The ID of the user to update.
     * @param userDTO The new data for the user.
     * @throws UserNotFoundException if the user does not exist.
     * @throws EntityUpdateFailException if updating the user fails.
     */
    public void updateUser(Integer id, UserDTO userDTO) throws UserNotFoundException, EntityUpdateFailException {
        User existingUser = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setFullname(userDTO.getFullname());
        existingUser.setRole(userDTO.getRole());
        existingUser.setPassword( passwordEncoder.encode( userDTO.getPassword() ) );
        this.updateEntity(existingUser);
    }

    /**
     * Updates a user's password.
     *
     * @param user The user whose password is being updated.
     * @param passwordChangeDTO The new password details.
     * @throws InvalidOldPasswordUpdateException if the old password is incorrect.
     * @throws NewEqualsOldPasswordUpdateException if the new password matches the old password.
     * @throws EntityUpdateFailException if updating the password fails.
     * @throws PasswordsAreNotEqualsException if the new password and confirmation password do not match.
     */
    public void updateUserPassword(User user, PasswordChangeDTO passwordChangeDTO) throws InvalidOldPasswordUpdateException, NewEqualsOldPasswordUpdateException, EntityUpdateFailException, PasswordsAreNotEqualsException {
        if(!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordUpdateException();
        }

        if(passwordChangeDTO.getOldPassword().equals(passwordChangeDTO.getNewPassword())) {
            throw new NewEqualsOldPasswordUpdateException();
        }

        if(!validatorService.passwordAreSame(passwordChangeDTO.getNewPassword(), passwordChangeDTO.getConfirmNewPassword())) {
            throw new PasswordsAreNotEqualsException();
        }

        user.setPassword( passwordEncoder.encode( passwordChangeDTO.getNewPassword() ) );
        this.updateEntity(user);
    }

    /**
     * Loads a user by their username for authentication.
     *
     * @param username The username to load.
     * @return UserDetails containing user information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Starting authentication process for user '{}'.", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("Authentication failed: User '{}' not found.", username);
                    return new UsernameNotFoundException("User not found");
                });
    }
}
