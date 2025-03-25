package com.nnk.springboot.exceptions.services;

import com.nnk.springboot.exceptions.CustomException;

public class EntitySaveFailException extends CustomException {
    public EntitySaveFailException() {
        super("Failed to update entity");
    }

    public EntitySaveFailException(String entityName) {
        super("Failed to save a '" + entityName + "' entity");
    }
}
