package com.nnk.springboot.exceptions.services;

import com.nnk.springboot.exceptions.CustomException;

public class EntityDeleteFailException extends CustomException {
    public EntityDeleteFailException() {
        super("Failed to delete entity");
    }

    public EntityDeleteFailException(String id) {
        super("Failed to delete entity with id '" + id + "'");
    }

    public EntityDeleteFailException(String id, String entityName) {
        super("Failed to delete a '" + entityName + "' entity with id '" + id + "'");
    }
}
