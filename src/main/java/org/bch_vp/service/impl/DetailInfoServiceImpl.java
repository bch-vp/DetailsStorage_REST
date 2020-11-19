package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.IdDetailInfo;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailInfoRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.DetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
    public void addQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject) {
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(EntityNotFoundException::new);
        detailInfo.getDetail().subtractAvailableDetails(quantity);
        detailInfo.addQuantityofDetailsUsed(quantity);
        flushAllRepositories();
    }

    @Override
    public boolean joinDetailAndProject(Integer quantityDetailsUsed, Long idDetail, Long idProject) {
        Project project=projectRepository.findById(idProject).get();
        Detail detail=detailRepository.findById(idDetail).get();
        if(quantityDetailsUsed<=detail.getQuantityOfAvailable()){
            detail.subtractAvailableDetails(quantityDetailsUsed);
            DetailInfo detailInfo=new DetailInfo(quantityDetailsUsed,detail,project);
           detailinfoRepository.save(detailInfo);
           detailRepository.save(detail);//delete
           projectRepository.save(project);//delete
        }
        flushAllRepositories();
        entityManager.clear();
        //rewrite
        return true;
    }

    @Override
    public List<DetailInfo> findAll() {
        return detailinfoRepository.findAll();
    }

    @Override
    public DetailInfo findById(Long idDetail, Long idProject) {
        return detailinfoRepository.findById(new IdDetailInfo(idDetail, idProject))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void subtractQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject) {
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(EntityNotFoundException::new);
        detailInfo.getDetail().addAvailableDetails(quantity);
        detailInfo.subtractQuantityofDetailsUsed(quantity);
        flushAllRepositories();
    }

    private void flushAllRepositories(){
        detailinfoRepository.flush();
        projectRepository.flush();
        detailRepository.flush();
    }
}
