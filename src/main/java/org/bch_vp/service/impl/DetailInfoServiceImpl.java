package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.IdDetailInfo;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailInfoRepository;
import org.bch_vp.repository.StorageRepository;
import org.bch_vp.service.DetailInfoService;
import org.bch_vp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.HashMap;
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
    public boolean addQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException {
        flushAndClear();
        HashMap mapRequestBody= JsonUtil.mapFromJson(jsonQuantityRequestBody, HashMap.class);
        Detail detail = detailRepository.findById(idDetail)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        projectRepository.findById(idProject)
                .orElseThrow(()->new EntityNotFoundException(Project.class));
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(DetailInfoNotFoundException::new);
        String quantity = String.valueOf(mapRequestBody.get("quantity"));
        if(quantity != null
                && !quantity.isEmpty()
                && quantity.matches("^[0-9]+$")
                && Integer.parseInt(quantity)>0
                && Integer.parseInt(quantity)<=detailInfo.getDetail().getQuantityOfAvailable()) {
            detail.subtractAvailableDetails(Integer.valueOf(quantity));
            detailInfo.addQuantityofDetailsUsed(Integer.valueOf(quantity));
                //write recalculate price
        } else {
            throw new QuantityOfDetailsException(quantity, String.valueOf(detail.getQuantityOfAvailable()));
        }
        Integer quantityExpected = detailInfo.getQuantityDetailsUsed();
        flushAndClear();
        return detailinfoRepository
                .findById(new IdDetailInfo(idDetail,idProject))
                .get()
                .getQuantityDetailsUsed()
                .equals(quantityExpected);
    }

    @Override
    public boolean joinDetailAndProject(Integer quantity, Long idDetail, Long idProject) throws QuantityOfDetailsException, EntityNotFoundException {
        flushAndClear();
        Project project = projectRepository.findById(idProject)
                .orElseThrow(()->new EntityNotFoundException(Project.class));
        Detail detail = detailRepository.findById(idDetail)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        if (quantity > detail.getQuantityOfAvailable() || quantity<1) {
            throw new QuantityOfDetailsException(String.valueOf(quantity), String.valueOf(detail.getQuantityOfAvailable()));
        }
        detail.subtractAvailableDetails(quantity);
        DetailInfo detailInfo = new DetailInfo(quantity, detail, project);
        detailinfoRepository.save(detailInfo);
        // detailRepository.save(detail);
        // projectRepository.save(project);
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
    public void subtractQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException {
//        flushAndClear();
//        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
//                .orElseThrow(()->new EntityNotFoundException(Project.class));
//        detailInfo.getDetail().addAvailableDetails(quantity);
//        detailInfo.subtractQuantityofDetailsUsed(quantity);
//        flushAndClear();
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
