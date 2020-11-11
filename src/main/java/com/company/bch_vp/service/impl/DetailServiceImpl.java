package com.company.bch_vp.service.impl;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.DetailInfo;
import com.company.bch_vp.entity.ExceptionHandler.EntityNotFoundException;
import com.company.bch_vp.entity.IdDetailInfo;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.repository.DetailRepository;
import com.company.bch_vp.repository.DetailinfoRepository;
import com.company.bch_vp.repository.ProjectRepository;
import com.company.bch_vp.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailinfoRepository detailinfoRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DetailRepository detailRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public Detail saveDetail(Detail detail) {
        if(detail.getQuantityOfAvailable() == null){
            detail.setQuantityOfAvailable(detail.getQuantityOfAll());
        }
        return detailRepository.save(detail);
    }

    @Override
    public boolean deleteDetailById(Long id) {
//        Optional<Detail> optionalDetail=detailRepository.findById(id);
//        if(!optionalDetail.isPresent()){
//            return;
//        }
//        Detail detail=optionalDetail.get();
//
//        detail.getDetailsInfo()
//                .stream()
//                .forEach(detailInfo ->{
//                    Long projectId = detailInfo.getId().getProjectId();
//                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
//                    detailInfo.getProject().getDetailsInfo().remove(detailInfo);
//                    Project project=detailInfo.getProject();
//                    detailinfoRepository.delete(detailInfo);
//
//                    project.getDetailsInfo()
//                            .stream()
//                            .forEach(detailInfo1 -> {
//                        detailInfo1.getDetail().addAvailableDetails(detailInfo1.getQuantityDetailsUsed());
//                        detailInfo1.getDetail().getDetailsInfo().remove(detailInfo1);
//                        detailinfoRepository.delete(detailInfo1);
//                    });
//                    projectRepository.delete(project);
//                });
//        detailRepository.deleteById(id);


        Optional<Detail> detailOptional=detailRepository.findById(id);
        if(!detailOptional.isPresent()){
            return false;
        }
        Detail detail= detailOptional.get();
        detail.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailinfoRepository.delete(detailInfo);
                });
        detailRepository.delete(detail);
        flushAllRepositories();
        return true;
    }

    @Override
    public void addAvailableDetails(Long id, Integer quantity) {
        detailRepository.save(detailRepository.findById(id).get().addAvailableDetails(quantity));
        flushAllRepositories();
    }

    @Override
    public void addQuantityOfDetails(Long id, Integer quantity) {
        detailRepository.save(detailRepository.findById(id).get().addQuantityOfDetails(quantity));
        flushAllRepositories();
    }

    @Override
    public void deleteProjectInDetail(Long idDetail, Long idProject){
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject));
        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
        detailinfoRepository.delete(detailInfo);
        flushAllRepositories();
    }

    @Override
    public boolean deleteAllProjectsFromDetail(Long id) throws EntityNotFoundException  {
       Detail detail = detailRepository.findById(id)
               .orElseThrow(EntityNotFoundException::new);
      detail.getDetailsInfo()
               .stream()
               .forEach(detailInfo -> {detailinfoRepository.delete(detailInfo);});
       entityManager.clear(); // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
       return detail.getDetailsInfo().isEmpty();
    }

    @Override
    public boolean deleteAllDetails() {
        detailRepository.deleteAll();
        flushAllRepositories();
        entityManager.clear();
        return detailRepository.findAll().isEmpty();
    }

    @Override
    public boolean updateDetail(Long id, Detail detailNew) {
        return detailRepository.findById(id)
                .map(detail -> {
                    detail.updateDetail(detailNew);
                    return true;
                })
                .orElseGet(()->{return false;});

//        Optional<Detail> detail= detailRepository.findById(id);
//        if(detail.isPresent()){
//            detail.get().updateDetail(detailNew);
//            return true;
//        }
//        return false;
    }

    @Override
    public Detail findDetailById(Long id) {
        return detailRepository.findById(id)
                .orElseGet(()->{
                    return null;
                });
    }

    @Override
    public List<Detail> findAll() {
        return detailRepository.findAll();
    }

    private void flushAllRepositories(){
        detailinfoRepository.flush();
        projectRepository.flush();
        detailRepository.flush();
    }

}
