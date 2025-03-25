package com.nnk.springboot.services;

import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for validating user-related information, particularly password management.
 */
@Service
public class ValidatorService {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Checks if the provided password matches the hashed password.
     *
     * @param hashedPassword The hashed password to compare.
     * @param password The plain password to validate.
     * @return true if the passwords match, false otherwise.
     */
    public boolean checkIsPasswordsMatching(String hashedPassword, String password) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    /**
     * Checks if the two provided passwords are the same.
     *
     * @param password The first password.
     * @param confirmPassword The second password.
     * @return true if the passwords are the same, false otherwise.
     */
    public boolean passwordAreSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
