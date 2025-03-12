package com.nnk.springboot.exceptions.services;

import com.nnk.springboot.exceptions.CustomException;

public class EntityNotFoundException extends CustomException {
    public EntityNotFoundException() {
        super("Failed to find entity");
    }

    public EntityNotFoundException(String id) {
        super("Failed to find entity with id '" + id + "'");
    }

    public EntityNotFoundException(String id, String entityName) {
        super("Failed to find a '" + entityName + "' entity with id '" + id + "'");
    }
}
