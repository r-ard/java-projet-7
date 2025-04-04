package com.nnk.springboot.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public abstract class AbstractControllerTests<Entity, Id> {
    protected static SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor getTestUser() {
        return user("testuser").roles("ADMIN");
    }

    protected abstract JpaRepository<Entity, Id> getRepository();

    protected abstract Entity generateTestEntity();

    protected Entity getEntity() {
        List<Entity> entities = this.getRepository().findAll();
        return entities.get(entities.size()-1);
    }

    @BeforeEach
    public void setUp() {
        getRepository().save( generateTestEntity() );
    }

    @AfterEach
    public void cleanUp() {
        getRepository().deleteAll();
    }
}
