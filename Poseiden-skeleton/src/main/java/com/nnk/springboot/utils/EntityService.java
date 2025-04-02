package com.nnk.springboot.utils;

import com.nnk.springboot.exceptions.services.EntityDeleteFailException;
import com.nnk.springboot.exceptions.services.EntityNotFoundException;
import com.nnk.springboot.exceptions.services.EntitySaveFailException;
import com.nnk.springboot.exceptions.services.EntityUpdateFailException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract service class for handling common CRUD operations for entities.
 * This class provides a set of methods to interact with the database via a JPA repository.
 * It allows for creating, reading, updating, and deleting entities.
 *
 * @param <Entity> The entity type that will be handled.
 * @param <DTO> The Data Transfer Object (DTO) type for the entity.
 * @param <EntityId> The type of the entity's ID.
 */
public abstract class EntityService<Entity, DTO, EntityId> {
    /**
     * Gets the repository associated with the entity.
     *
     * @return The JPA repository for the entity.
     */
    protected abstract JpaRepository<Entity, EntityId> getRepository();

    /**
     * Maps an entity to its corresponding DTO.
     *
     * @param entity The entity to map.
     * @return The corresponding DTO.
     */
    protected abstract DTO mapEntity(Entity entity);

    /**
     * Maps a DTO to its corresponding entity.
     *
     * @param dto The DTO to map.
     * @return The corresponding entity.
     */
    protected abstract Entity mapFromDTO(DTO dto);

    /**
     * Sets the ID of the entity.
     *
     * @param entity The entity to set the ID for.
     * @param id The ID to set.
     */
    protected abstract void setEntityId(Entity entity, EntityId id);

    /**
     * Gets the ID of the entity.
     *
     * @param entity The entity to get the ID from.
     * @return The ID of the entity.
     */
    protected abstract EntityId getEntityID(Entity entity);

    /**
     * Gets the name of the entity class.
     *
     * @return The name of the entity class.
     */
    protected abstract String getEntityName();

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity to find.
     * @return The DTO representing the entity.
     * @throws EntityNotFoundException If the entity is not found.
     */
    public DTO findById(EntityId id) throws EntityNotFoundException {
        Entity entity = this.getRepository().findById(id).orElse(null);

        if(entity == null) {
            throw new EntityNotFoundException(id.toString(), this.getEntityName());
        }

        return this.mapEntity(entity);
    }

    /**
     * Finds all entities.
     *
     * @return A list of DTOs representing all entities.
     */
    public List<DTO> findAll() {
        List<Entity> entities = this.getRepository().findAll();

        List<DTO> out = new ArrayList<>();
        for(Entity entity : entities) {
            out.add( this.mapEntity(entity) );
        }

        return out;
    }

    /**
     * Updates an entity with the given ID and DTO.
     *
     * @param id The ID of the entity to update.
     * @param dto The DTO containing the updated data.
     * @throws EntityUpdateFailException If the entity update fails.
     */
    public void update(EntityId id, DTO dto) throws EntityUpdateFailException {
        Entity entity = this.mapFromDTO(dto);
        this.setEntityId(entity, id);

        this.updateEntity(entity);
    }

    /**
     * Updates an entity in the repository.
     *
     * @param entity The entity to update.
     * @throws EntityUpdateFailException If the entity update fails.
     */
    public void updateEntity(Entity entity) throws EntityUpdateFailException {
        try {
            if(!this.getRepository().existsById( this.getEntityID(entity) )) {
                throw new Exception();
            }

            Entity updatedEntity = this.getRepository().save(entity);
        }
        catch(Exception ex) {
            throw new EntityUpdateFailException(this.getEntityID(entity).toString() , this.getEntityName());
        }
    }

    /**
     * Saves a new entity from the given DTO.
     *
     * @param dto The DTO containing the data to save.
     * @return The ID of the saved entity.
     * @throws EntitySaveFailException If the entity save fails.
     */
    public EntityId save(DTO dto) throws EntitySaveFailException {
        Entity entity = this.mapFromDTO(dto);
        return this.saveEntity(entity);
    }

    /**
     * Saves an entity in the repository.
     *
     * @param entity The entity to save.
     * @return The ID of the saved entity.
     * @throws EntitySaveFailException If the entity save fails.
     */
    protected EntityId saveEntity(Entity entity) throws EntitySaveFailException {
        try {
            Entity savedEntity = this.getRepository().save(entity);
            return this.getEntityID(savedEntity);
        }
        catch(Exception ex) {
            throw new EntitySaveFailException(this.getEntityName());
        }
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to delete.
     * @throws EntityDeleteFailException If the entity deletion fails.
     */
    public void delete(EntityId id) throws EntityDeleteFailException {
        try {
            this.getRepository().deleteById(id);
        }
        catch(Exception ex) {
            throw new EntityDeleteFailException(id.toString(), this.getEntityName());
        }
    }
}
