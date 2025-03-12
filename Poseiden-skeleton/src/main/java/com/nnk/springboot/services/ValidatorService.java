package com.nnk.springboot.services;

import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkIsPasswordsMatching(String hashedPassword, String password) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public boolean passwordAreSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
