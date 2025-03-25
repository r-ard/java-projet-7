package com.nnk.springboot.exceptions.user;

import com.nnk.springboot.exceptions.CustomException;

public class PasswordsAreNotEqualsException extends CustomException {
    public PasswordsAreNotEqualsException() {
        super("Passwords are not equals");
    }
}
