package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailRepository;
import org.bch_vp.repository.ProjectRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl
        extends AbstractStorageServiceImpl<Detail, Project, DetailRepository, ProjectRepository>
        implements DetailService {

    @Autowired
    private StorageRepository<Detail> detailRepository;

    private DetailServiceImpl() {
        super(Detail.class, Project.class);
    }

    @Override
    public Detail addAvailableDetails(Long id, Integer quantity) throws EntityNotFoundException {
        Detail detail = detailRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        detail=detailRepository.save(detail.addAvailableDetails(quantity));
        detailRepository.flush();
        return detail;
    }

    @Override
    public Detail addQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException {
        Detail detail = detailRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        detail=detailRepository.save(detail.addQuantityOfDetails(quantity));
        detailRepository.flush();
        return detail;
    }
}
