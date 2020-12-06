package org.bch_vp.service.impl;

import org.bch_vp.entity.*;
import org.bch_vp.entity.exception_handler.entity.*;
import org.bch_vp.repository.DetailInfoRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.StorageService;
import org.bch_vp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public abstract class AbstractStorageServiceImpl<Entity extends AbstractEntity,
        InnerEntity extends AbstractEntity,
        EntityRepository extends StorageRepository<Entity>,
        InnerEntityRepository extends StorageRepository<InnerEntity>> implements StorageService<Entity, InnerEntity> {

    private Class<?> entityClass;
    private Class<?> innerEntityClass;

    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private InnerEntityRepository innerEntityRepository;
    @Autowired
    private DetailInfoRepository detailinfoRepository;
    @Autowired
    private EntityManager entityManager;

    protected AbstractStorageServiceImpl(Class<?> entityClass, Class<?> innerEntityClass) {
        this.entityClass = entityClass;
        this.innerEntityClass = innerEntityClass;
    }

    @Override
    public Entity saveEntity(Entity entity) throws IdNotValidException {
        flushAndClear();
        if(entity instanceof Detail) {
            Detail detail = (Detail) entity;
            if (detail.getQuantityOfAvailable() == null) {  //rewrite
                detail.setQuantityOfAvailable(detail.getQuantityOfAll());
            }

            if (detail.getId() != null) { // rewrite
                throw new IdNotValidException();
            }
        }
        return entityRepository.save(entity);
    }

    @Override
    public boolean deleteEntityById(Long id) throws EntityNotFoundException {
        flushAndClear();
        Entity entity = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityClass));
        entity.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    if (entity instanceof Project) {
                        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
                    }
                    detailinfoRepository.delete(detailInfo);
                });
        entityRepository.delete(entity);
        flushAndClear();
        return !entityRepository.findById(id).isPresent();
    }

    @Override
    public List<Entity> findAll() {
        flushAndClear();
        return entityRepository.findAll();
    }

    @Override
    public Entity findEntityById(Long id) throws EntityNotFoundException {
        flushAndClear();
        return entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityClass));
    }

    @Override
    public boolean deleteAllEntities() {
        flushAndClear();
        entityRepository.findAll()
                .stream()
                .map(Entity::getDetailsInfo)
                .forEach(detailInfos -> {
                    detailInfos
                            .stream()
                            .forEach(detailInfo -> {
                                if (entityClass.getSimpleName().equals("Project")) {
                                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
                                }
                                detailinfoRepository.delete(detailInfo);
                            });
                });
        entityRepository.deleteAll();
        flushAndClear();
        return entityRepository.findAll().isEmpty();
    }

    @Override //rewrite!!!!!!!
    public Entity updateEntity(Long id, String jsonRequestBody) throws EntityNotFoundException, IOException, QuantityOfDetailsException, NumberOfQuantityException {
        flushAndClear();
        HashMap mapRequestBody= JsonUtil.mapFromJson(jsonRequestBody, HashMap.class);
        Entity entity = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityClass));
        entity=(Entity)entity.update(mapRequestBody);
        flushAndClear();
        return entity;
    }

    @Override
    public boolean deleteAllInnerEntitiesFromEntity(Long id) throws EntityNotFoundException {
        flushAndClear();
        Entity entity = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityClass));
        entity.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
                    detailinfoRepository.delete(detailInfo);
                });
        flushAndClear();
        return entityRepository.findById(id).get().getDetailsInfo().isEmpty();
    }

    @Override
    public boolean deleteInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException {
        flushAndClear();
        Entity entity = entityRepository.findById(idEntity)
                .orElseThrow(() -> new EntityNotFoundException(entityClass));
        InnerEntity innerEntity = innerEntityRepository.findById(idInnerEntity)
                .orElseThrow(() -> new EntityNotFoundException(innerEntityClass));
        DetailInfo detailInfo;
        if (entity instanceof Detail && innerEntity instanceof Project) {
            detailInfo = detailinfoRepository.findById(new IdDetailInfo(entity.getId(), innerEntity.getId()))
                    .orElseThrow(DetailInfoNotFoundException::new);
        } else {
            detailInfo = detailinfoRepository.findById(new IdDetailInfo(innerEntity.getId(), entity.getId()))
                    .orElseThrow(DetailInfoNotFoundException::new);
        }
        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
        detailinfoRepository.delete(detailInfo);
        flushAndClear();
        return !detailinfoRepository.findById(detailInfo.getId()).isPresent();
    }

    @Override
    public InnerEntity findInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException {
        flushAndClear();
        Entity entity = entityRepository.findById(idEntity)
                .orElseThrow(() -> new EntityNotFoundException(entityClass));
        InnerEntity innerEntity = innerEntityRepository.findById(idInnerEntity)
                .orElseThrow(() -> new EntityNotFoundException(innerEntityClass));
        DetailInfo detailInfo;
        if (entity instanceof Detail && innerEntity instanceof Project) {
            detailInfo = detailinfoRepository.findById(new IdDetailInfo(entity.getId(), innerEntity.getId()))
                    .orElseThrow(DetailInfoNotFoundException::new);
            return (InnerEntity) detailInfo.getProject();
        } else {
            detailInfo = detailinfoRepository.findById(new IdDetailInfo(innerEntity.getId(), entity.getId()))
                    .orElseThrow(DetailInfoNotFoundException::new);
            return (InnerEntity) detailInfo.getDetail();
        }
    }

    private void flushAndClear() {
        detailinfoRepository.flush();
        entityRepository.flush();
        innerEntityRepository.flush();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
    }


}
