package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public abstract class AbstractControllerTests<Entity, Id> {
    protected static SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor getTestUser() {
        return user("testuser").roles("USER");
    }

    protected abstract JpaRepository<Entity, Id> getRepository();

    protected abstract Entity generateTestEntity();

    protected Entity getEntity() {
        return this.getRepository().findAll().get(0);
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
