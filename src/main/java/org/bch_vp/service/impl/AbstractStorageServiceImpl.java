package org.bch_vp.service.impl;

import org.bch_vp.entity.*;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import org.bch_vp.repository.DetailinfoRepository;
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

    private final Class<?> typeParameterClass;

    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private InnerEntityRepository innerEntityRepository;
    @Autowired
    private DetailinfoRepository detailinfoRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    AbstractStorageServiceImpl(Class<?> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }


    @Override
    public Entity saveEntity(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    public boolean deleteEntityById(Long id) throws EntityNotFoundException {
        ////        Optional<Detail> optionalDetail=detailRepository.findById(id);
////        if(!optionalDetail.isPresent()){
////            return;
////        }
////        Detail detail=optionalDetail.get();
////
////        detail.getDetailsInfo()
////                .stream()
////                .forEach(detailInfo ->{
////                    Long projectId = detailInfo.getId().getProjectId();
////                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
////                    detailInfo.getProject().getDetailsInfo().remove(detailInfo);
////                    Project project=detailInfo.getProject();
////                    detailinfoRepository.delete(detailInfo);
////
////                    project.getDetailsInfo()
////                            .stream()
////                            .forEach(detailInfo1 -> {
////                        detailInfo1.getDetail().addAvailableDetails(detailInfo1.getQuantityDetailsUsed());
////                        detailInfo1.getDetail().getDetailsInfo().remove(detailInfo1);
////                        detailinfoRepository.delete(detailInfo1);
////                    });
////                    projectRepository.delete(project);
////                });
////        detailRepository.deleteById(id);
        InnerEntity innerEntity= innerEntityRepository.findById(1L)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
        Entity entity= entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
        entity.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailinfoRepository.delete(detailInfo);
                    //rewrite
//                    if(entity instanceof Project){
//                        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
//                    }
                });
        entityRepository.delete(entity);
        flushAllRepositories();
        entityManager.clear();
         innerEntity= innerEntityRepository.findById(1L)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return !entityRepository.findById(id).isPresent();
    }

    @Override
    public List<Entity> findAll() {
        return entityRepository.findAll();
    }

    @Override
    public Entity findEntityById(Long id) throws EntityNotFoundException {
        return entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
    }

    @Override
    public boolean deleteAllEntities() {
        entityRepository.deleteAll();
        flushAllRepositories();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return entityRepository.findAll().isEmpty();
    }

    @Override
    public Entity updateEntity(Long id, Entity newEntity) throws EntityNotFoundException {
        Entity entity=entityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
        return (Entity) entity.update(newEntity);
    }

    @Override
    public boolean deleteAllInnerEntitiesFromEntity(Long id) throws EntityNotFoundException {
        return false;
    }

    @Override
    public boolean deleteInnerEntityFromEntity(Long idEntity, Long idInnerEntity) throws EntityNotFoundException, DetailInfoNotFoundException {
        Entity entity = entityRepository.findById(idEntity)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
        InnerEntity innerEntity = innerEntityRepository.findById(idInnerEntity)
                .orElseThrow(()->new EntityNotFoundException(typeParameterClass.getClass().getName()));
        if (entity.getDetailsInfo().contains(innerEntity)) {
            throw new DetailInfoNotFoundException();
        }
        DetailInfo detailInfo = entity.getDetailsInfo()
                .stream()
                .findFirst()
                .orElseThrow(DetailInfoNotFoundException::new);
        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
        detailinfoRepository.delete(detailInfo);
        flushAllRepositories();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return !detailinfoRepository.findById(detailInfo.getId()).isPresent();
    }

    @Override
    public InnerEntity findInnerEntityFromEntity(Long idEntity, Long idInnerEntity) {
        return null;
    }

    private void flushAllRepositories(){
        detailinfoRepository.flush();
        entityRepository.flush();
        innerEntityRepository.flush();
    }


}
