package com.nnk.springboot.exceptions.services;

import com.nnk.springboot.exceptions.CustomException;

public class EntityUpdateFailException extends CustomException {
    public EntityUpdateFailException() {
        super("Failed to update entity");
    }

    public EntityUpdateFailException(String id) {
        super("Failed to update entity with id '" + id + "'");
    }

    public EntityUpdateFailException(String id, String entityName) {
        super("Failed to update a '" + entityName + "' entity with id '" + id + "'");
    }
}
