package com.nnk.springboot.utils;

import com.nnk.springboot.exceptions.services.EntityDeleteFailException;
import com.nnk.springboot.exceptions.services.EntityNotFoundException;
import com.nnk.springboot.exceptions.services.EntitySaveFailException;
import com.nnk.springboot.exceptions.services.EntityUpdateFailException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityService<Entity, DTO, EntityId> {
    protected abstract JpaRepository<Entity, EntityId> getRepository();

    protected abstract DTO mapEntity(Entity entity);

    protected abstract Entity mapFromDTO(DTO dto);

    protected abstract void setEntityId(Entity entity, EntityId id);

    protected abstract EntityId getEntityID(Entity entity);

    protected abstract String getEntityName();

    public DTO findById(EntityId id) throws EntityNotFoundException {
        Entity entity = this.getRepository().findById(id).orElse(null);

        if(entity == null) {
            throw new EntityNotFoundException(id.toString(), this.getEntityName());
        }

        return this.mapEntity(entity);
    }

    public List<DTO> findAll() {
        List<Entity> entities = this.getRepository().findAll();

        List<DTO> out = new ArrayList<>();
        for(Entity entity : entities) {
            out.add( this.mapEntity(entity) );
        }

        return out;
    }

    public void update(EntityId id, DTO dto) throws EntityUpdateFailException {
        Entity entity = this.mapFromDTO(dto);
        this.setEntityId(entity, id);

        this.updateEntity(entity);
    }

    public void updateEntity(Entity entity) throws EntityUpdateFailException {
        try {
            this.getRepository().save(entity);
        }
        catch(Exception ex) {
            throw new EntityUpdateFailException(id.toString(), this.getEntityName());
        }
    }

    public EntityId save(DTO dto) throws EntitySaveFailException {
        Entity entity = this.mapFromDTO(dto);
        this.saveEntity(entity);
    }

    protected EntityId saveEntity(Entity entity) throws EntitySaveFailException {
        try {
            Entity savedEntity = this.getRepository().save(entity);
            return this.getEntityID(savedEntity);
        }
        catch(Exception ex) {
            throw new EntitySaveFailException(this.getEntityName());
        }
    }

    public boolean delete(EntityId id) throws EntityDeleteFailException {
        try {
            this.getRepository().deleteById(id);
        }
        catch(Exception ex) {
            throw new EntityDeleteFailException(id, this.getEntityName());
        }
    }
}
