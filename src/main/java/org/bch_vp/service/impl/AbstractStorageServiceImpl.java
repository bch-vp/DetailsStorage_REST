package org.bch_vp.service.impl;

import org.bch_vp.entity.*;
import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.repository.DetailInfoRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
        this.innerEntityClass=innerEntityClass;
    }

    @Override
    public Entity saveEntity(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    public boolean deleteEntityById(Long id) throws EntityNotFoundException {
        Entity entity= entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(entityClass));
        entity.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                   //detailInfo.breakRelations(detailInfo);
                   detailinfoRepository.delete(detailInfo);
                    //rewrite
                    if(entity instanceof Project){
                        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
                    }
                });
//        List<DetailInfo> detailsInfo=entity.getDetailsInfo();
//        DetailInfo detailInfo=detailsInfo.get(0);
//        detailInfo.getProject().getDetailsInfo().remove(detailInfo);
//        detailInfo.getDetail().getDetailsInfo().remove(detailInfo);
//        detailinfoRepository.delete(detailInfo);
//        detailinfoRepository.flush();

        entityRepository.delete(entity);
        flushAndClear();
        return !entityRepository.findById(id).isPresent();
    }

    @Override
    public List<Entity> findAll() {
        return entityRepository.findAll();
    }

    @Override
    public Entity findEntityById(Long id) throws EntityNotFoundException {
        return entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(entityClass));
    }

    @Override
    public boolean deleteAllEntities() {
        entityRepository.deleteAll();
        flushAndClear();
        return entityRepository.findAll().isEmpty();
    }

    @Override //rewrite!!!!!!!
    public Entity updateEntity(Long id, Entity newEntity) throws EntityNotFoundException {
        Entity entity=entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(entityClass));
        return (Entity) entity.update(newEntity);
    }

    @Override
    public boolean deleteAllInnerEntitiesFromEntity(Long id) throws EntityNotFoundException {
        Entity entity=entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(entityClass));
        entity.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailinfoRepository.delete(detailInfo);
                });
        flushAndClear();
        return entityRepository.findById(id).get().getDetailsInfo().isEmpty();
    }

    @Override
    public boolean deleteInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException {
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

    private void flushAndClear(){
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
