package org.bch_vp.details_storage.service.impl;

import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.entity.DetailInfo;
import org.bch_vp.details_storage.entity.IdDetailInfo;
import org.bch_vp.details_storage.entity.Project;
import org.bch_vp.details_storage.exception_handler.exception.DetailInfoNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.EntityNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.QuantityOfDetailsException;
import org.bch_vp.details_storage.repository.DetailInfoRepository;
import org.bch_vp.details_storage.repository.StorageRepository;
import org.bch_vp.details_storage.util.JsonUtil;
import org.bch_vp.details_storage.service.DetailInfoService;
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
       addQuantityOfDetailsInProject(quantity, detail, detailInfo);

        Integer quantityExpected = detailInfo.getQuantityDetailsUsed();
        flushAndClear();
        return detailinfoRepository
                .findById(new IdDetailInfo(idDetail,idProject))
                .get()
                .getQuantityDetailsUsed()
                .equals(quantityExpected);
    }

    private void addQuantityOfDetailsInProject(String quantity, Detail detail, DetailInfo detailInfo) throws QuantityOfDetailsException {
        if(quantity != null
                && !quantity.isEmpty()
                && quantity.matches("^[0-9]+$")
                && Integer.parseInt(quantity)>0
                && Integer.parseInt(quantity)<=detailInfo.getDetail().getQuantityOfAvailable()) {
            detail.subtractAvailableDetails(Integer.valueOf(quantity));
            detailInfo.addQuantityofDetailsUsed(Integer.valueOf(quantity));
        } else {
            throw new QuantityOfDetailsException(quantity, String.valueOf(detail.getQuantityOfAvailable()));
        }
    }

    @Override
    public boolean subtractQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException {
        flushAndClear();
        HashMap mapRequestBody= JsonUtil.mapFromJson(jsonQuantityRequestBody, HashMap.class);
        Detail detail = detailRepository.findById(idDetail)
                .orElseThrow(()->new EntityNotFoundException(Detail.class));
        projectRepository.findById(idProject)
                .orElseThrow(()->new EntityNotFoundException(Project.class));
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(DetailInfoNotFoundException::new);

        String quantity = String.valueOf(mapRequestBody.get("quantity"));
        subtractQuantityOfDetailsInProject(quantity, detail, detailInfo);

        Integer quantityExpected = detailInfo.getQuantityDetailsUsed();
        flushAndClear();
        return detailinfoRepository
                .findById(new IdDetailInfo(idDetail,idProject))
                .get()
                .getQuantityDetailsUsed()
                .equals(quantityExpected);
    }

    private void subtractQuantityOfDetailsInProject(String quantity, Detail detail, DetailInfo detailInfo) throws QuantityOfDetailsException {
        if(quantity != null
                && !quantity.isEmpty()
                && quantity.matches("^[0-9]+$")
                && Integer.parseInt(quantity)>0
                && Integer.parseInt(quantity)<=detailInfo.getQuantityDetailsUsed()) {
            detail.addAvailableDetails(Integer.valueOf(quantity));
            detailInfo.subtractQuantityofDetailsUsed(Integer.valueOf(quantity));
        } else {
            throw new QuantityOfDetailsException(quantity, String.valueOf(detail.getQuantityOfAvailable()));
        }
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
