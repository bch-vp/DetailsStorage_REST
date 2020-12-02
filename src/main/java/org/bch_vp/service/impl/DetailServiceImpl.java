package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailRepository;
import org.bch_vp.repository.ProjectRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class DetailServiceImpl
        extends AbstractStorageServiceImpl<Detail, Project, DetailRepository, ProjectRepository>
        implements DetailService {

    @Autowired
    private StorageRepository<Detail> detailRepository;
    @Autowired
    private EntityManager entityManager;

    private DetailServiceImpl() {
        super(Detail.class, Project.class);
    }

    @Override
    public Detail addAvailableDetails(Long id, Integer quantity) throws EntityNotFoundException {
        flushAndClear();
        Detail detail = detailRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        detail=detailRepository.save(detail.addAvailableDetails(quantity));
        detailRepository.flush();
        return detail;
    }

    @Override
    public Detail addQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException, QuantityOfDetailsException {
        if(quantity < 1){
            throw new QuantityOfDetailsException(String.valueOf(quantity));
        }
        Detail detail = detailRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        detail=detailRepository.save(detail.addQuantityOfDetails(quantity));
        detailRepository.flush();
        return detail;
    }

    @Override
    public Detail subtractQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException, QuantityOfDetailsException {
        if(quantity < 1){
            throw new QuantityOfDetailsException(String.valueOf(quantity));
        }
        Detail detail = detailRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        if(quantity > detail.getQuantityOfAvailable()){
            throw new QuantityOfDetailsException(String.valueOf(quantity), String.valueOf(detail.getQuantityOfAvailable()));
        }
        detail=detailRepository.save(detail.subtractQuantityOfDetails(quantity));
        detailRepository.flush();
        return detail;
    }

    private void flushAndClear(){
        detailRepository.flush();
        entityManager.clear();
    }
}
