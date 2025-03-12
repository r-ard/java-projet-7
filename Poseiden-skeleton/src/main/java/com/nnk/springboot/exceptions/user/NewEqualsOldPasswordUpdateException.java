package com.nnk.springboot.exceptions.user;

import com.nnk.springboot.exceptions.CustomException;

public class NewEqualsOldPasswordUpdateException extends CustomException {
    public NewEqualsOldPasswordUpdateException() { super("New password and old password is the same"); }
}
