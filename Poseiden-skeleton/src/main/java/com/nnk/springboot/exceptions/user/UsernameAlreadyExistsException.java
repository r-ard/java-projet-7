package com.nnk.springboot.exceptions.user;

import com.nnk.springboot.exceptions.CustomException;

public class UsernameAlreadyExistsException extends CustomException {
    public UsernameAlreadyExistsException(String username) {
        super("Username alredy exists : " + username);
    }

    public UsernameAlreadyExistsException() {
        super("Username already exists");
    }
}
