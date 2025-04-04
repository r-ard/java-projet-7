package com.nnk.springboot.exceptions.user;

import com.nnk.springboot.exceptions.CustomException;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException() {
        super("Invalid password");
    }
}
