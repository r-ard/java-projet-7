package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exceptions.user.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidatorService validatorService;

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> out = new ArrayList<>();

        for(User user : users) {
            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());
            userDTO.setFullname(user.getFullname());
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());

            out.add(userDTO);
        }

        return out;
    }

    public UserDTO getUserById(Integer id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullname(user.getFullname());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        return userDTO;
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
