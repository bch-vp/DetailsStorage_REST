package org.bch_vp.service;

import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.AbstractEntity;

import java.util.List;

public interface StorageService<Entity extends AbstractEntity, InnerEntity extends AbstractEntity> {
    Entity saveEntity(Entity detail);
    boolean deleteEntityById(Long id) throws EntityNotFoundException;
    List<Entity> findAll();
    Entity findEntityById(Long id) throws EntityNotFoundException;
    boolean deleteAllEntities();
    Entity updateEntity(Long id, Entity entity) throws EntityNotFoundException;
    boolean deleteAllInnerEntitiesFromEntity(Long id) throws  EntityNotFoundException;
    boolean deleteInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException;
    InnerEntity findInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException;
}
