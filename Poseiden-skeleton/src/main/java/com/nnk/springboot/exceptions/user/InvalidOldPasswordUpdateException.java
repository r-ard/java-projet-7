package com.nnk.springboot.exceptions.user;

import com.nnk.springboot.exceptions.CustomException;

public class InvalidOldPasswordUpdateException extends CustomException {
    public InvalidOldPasswordUpdateException() {
        super("Invalid old password");
    }
}
