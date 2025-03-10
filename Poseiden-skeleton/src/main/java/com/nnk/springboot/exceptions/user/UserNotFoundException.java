package com.nnk.springboot.exceptions.user;

import com.nnk.springboot.exceptions.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(int userId) {
        super("Can not find user with id : " + String.valueOf(userId));
    }

    public UserNotFoundException() {
        super("Can not find user");
    }
}
