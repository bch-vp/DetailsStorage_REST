package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.IdDetailInfo;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailInfoRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.DetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class DetailInfoServiceImpl implements DetailInfoService {
    @Autowired
    private StorageRepository<Project> projectRepository;
    @Autowired
    private StorageRepository<Detail> detailRepository;
    @Autowired
    private DetailInfoRepository detailinfoRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public void addQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject) throws EntityNotFoundException {
        flushAndClear();
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(()->new EntityNotFoundException(DetailInfo.class));
        detailInfo.getDetail().subtractAvailableDetails(quantity);
        detailInfo.addQuantityofDetailsUsed(quantity);
        flushAndClear();
    }

    @Override
    public boolean joinDetailAndProject(Integer quantityDetailsUsed, Long idDetail, Long idProject) throws QuantityOfDetailsException, EntityNotFoundException {
        flushAndClear();
        Project project = projectRepository.findById(idProject)
                .orElseThrow(()->new EntityNotFoundException(Project.class));
        Detail detail = detailRepository.findById(idDetail)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        if (quantityDetailsUsed > detail.getQuantityOfAvailable() || quantityDetailsUsed<0) {
            throw new QuantityOfDetailsException();
        }
        detail.subtractAvailableDetails(quantityDetailsUsed);
        DetailInfo detailInfo = new DetailInfo(quantityDetailsUsed, detail, project);
        detailinfoRepository.save(detailInfo);
        flushAndClear();
        return detailinfoRepository.findById(new IdDetailInfo(idDetail, idProject)).isPresent();
    }


    @Override
    public List<DetailInfo> findAll() {
        flushAndClear();
        return detailinfoRepository.findAll();
    }

    @Override
    public DetailInfo findById(Long idDetail, Long idProject) throws EntityNotFoundException {
        flushAndClear();
        return detailinfoRepository.findById(new IdDetailInfo(idDetail, idProject))
                .orElseThrow(()->new EntityNotFoundException(DetailInfo.class));
    }

    @Override
    public void subtractQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject) throws EntityNotFoundException {
        flushAndClear();
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(()->new EntityNotFoundException(Project.class));
        detailInfo.getDetail().addAvailableDetails(quantity);
        detailInfo.subtractQuantityofDetailsUsed(quantity);
        flushAndClear();
    }

    private void flushAndClear(){
        detailinfoRepository.flush();
        projectRepository.flush();
        detailRepository.flush();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
    }
}
