package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dto.auth.PasswordChangeDTO;
import com.nnk.springboot.dto.auth.RegisterDTO;
import com.nnk.springboot.exceptions.services.EntityNotFoundException;
import com.nnk.springboot.exceptions.services.EntityUpdateFailException;
import com.nnk.springboot.exceptions.user.InvalidOldPasswordUpdateException;
import com.nnk.springboot.exceptions.user.NewEqualsOldPasswordUpdateException;
import com.nnk.springboot.exceptions.user.PasswordsAreNotEqualsException;
import com.nnk.springboot.exceptions.user.UserNotFoundException;
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

    public UserDTO findByUsername(String username) throws EntityNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
        return this.mapEntity(user);
    }

    public boolean registerUser(RegisterDTO registerDTO) throws PasswordsAreNotEqualsException {
        if(!validatorService.passwordAreSame(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
            throw new PasswordsAreNotEqualsException();
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
