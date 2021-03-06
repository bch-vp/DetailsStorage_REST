package org.bch_vp.details_storage.service;

import org.bch_vp.details_storage.entity.AbstractEntity;
import org.bch_vp.details_storage.exception_handler.exception.*;

import java.io.IOException;
import java.util.List;

public interface StorageService<Entity extends AbstractEntity, InnerEntity extends AbstractEntity> {
    Entity saveEntity(Entity detail) throws IdNotValidException;
    boolean deleteEntityById(Long id) throws EntityNotFoundException;
    List<Entity> findAll();
    Entity findEntityById(Long id) throws EntityNotFoundException;
    boolean deleteAllEntities();
    Entity updateEntity(Long id, String jsonRequestBody) throws EntityNotFoundException, IOException, QuantityOfDetailsException, NumberOfQuantityException;
    boolean deleteAllInnerEntitiesFromEntity(Long id) throws  EntityNotFoundException;
    boolean deleteInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException;
    InnerEntity findInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException;
}
