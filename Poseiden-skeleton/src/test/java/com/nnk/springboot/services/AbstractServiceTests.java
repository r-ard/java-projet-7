package com.nnk.springboot.services;

import com.nnk.springboot.exceptions.services.EntityNotFoundException;
import com.nnk.springboot.exceptions.services.EntityUpdateFailException;
import com.nnk.springboot.utils.EntityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public abstract class AbstractServiceTests<Entity, DTO, Id> {
    protected abstract EntityService<Entity, DTO, Id> getService();

    protected abstract JpaRepository<Entity, Id> getRepository();

    protected abstract Entity generateTestEntity();

    protected abstract void updateTestDTO(DTO dto);

    protected abstract boolean isDTODifferent(DTO aDto, DTO bDto);

    protected abstract Id getEntityId(Entity entity);

    protected abstract Id getDTOId(DTO dto);

    protected Entity getLatestStoredEntity() throws Exception {
        List<Entity> entities = getRepository().findAll();

        if(entities.isEmpty()) {
            throw new Exception("No entities is stored in database");
        }

        return entities.get(entities.size()-1);
    }

    @BeforeEach
    public void setup() {
        getRepository().save( generateTestEntity() );
    }

    @AfterEach
    public void cleanUp() {
        getRepository().deleteAll();
    }

    @Test
    protected void testFindAll() throws Exception {
        List<DTO> data = getService().findAll();

        assertNotEquals(data.size(), 0);
    }

    @Test
    protected void testFindById() throws Exception {
        Entity latestStoredEntity = getLatestStoredEntity();
        Id entityId = getEntityId(latestStoredEntity);

        DTO dto = getService().findById(entityId);

        assertEquals(getDTOId(dto), entityId);
    }

    @Test
    protected void testUpdate() throws Exception {
        Entity latestStoredEntity = getLatestStoredEntity();
        Id entityId = getEntityId(latestStoredEntity);

        DTO originalDto = getService().findById(entityId);

        DTO dto = getService().findById(entityId);
        updateTestDTO(dto);

        getService().update(entityId, dto);

        DTO updatedDto = getService().findById(entityId);
        assertTrue(isDTODifferent(originalDto, updatedDto));
    }

    @Test
    protected void testUpdateOnNonExistantEntity() throws Exception {
        Entity latestStoredEntity = getLatestStoredEntity();
        Id entityId = getEntityId(latestStoredEntity);

        DTO dto = getService().findById(entityId);
        updateTestDTO(dto);

        getService().delete(entityId);

        assertThrows(EntityUpdateFailException.class, () -> {
            getService().update(entityId, dto);
        });
    }

    @Test
    protected void testDelete() throws Exception {
        Entity latestStoredEntity = getLatestStoredEntity();
        Id entityId = getEntityId(latestStoredEntity);

        getService().delete(entityId);

        assertThrows(EntityNotFoundException.class, () -> {
           getService().findById(entityId);
        });
    }
}
