package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.ProjectNotFoundException;
import org.bch_vp.entity.IdDetailInfo;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailRepository;
import org.bch_vp.repository.DetailinfoRepository;
import org.bch_vp.repository.ProjectRepository;
import org.bch_vp.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

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
    public boolean deleteDetailById(Long id) throws DetailNotFoundException {
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

        Detail detail= detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
        detail.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailinfoRepository.delete(detailInfo);
                });
        detailRepository.delete(detail);
        flushAllRepositories();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return !detailRepository.findById(id).isPresent();
    }

    @Override
    public Detail addAvailableDetails(Long id, Integer quantity) throws DetailNotFoundException {
        Detail detail = detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
        detail=detailRepository.save(detail.addAvailableDetails(quantity));
        flushAllRepositories();
        return detail;
    }

    @Override
    public Detail addQuantityOfDetails(Long id, Integer quantity) throws DetailNotFoundException {
        Detail detail = detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
        detail=detailRepository.save(detail.addQuantityOfDetails(quantity));
        flushAllRepositories();
        return detail;
    }

    @Override
    public Detail deleteProjectInDetail(Long idDetail, Long idProject) throws DetailNotFoundException, ProjectNotFoundException, DetailInfoNotFoundException {
        Detail detail = detailRepository.findById(idDetail)
                .orElseThrow(DetailNotFoundException::new);
        Project project=projectRepository.findById(idProject)
                .orElseThrow(ProjectNotFoundException::new);
        DetailInfo detailInfo= detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
                .orElseThrow(DetailInfoNotFoundException::new);
        detail.addAvailableDetails(detailInfo.getQuantityDetailsUsed());
        detailinfoRepository.delete(detailInfo);
        flushAllRepositories();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return detailRepository.findById(idDetail)
                .orElseThrow(DetailNotFoundException::new);
    }

    @Override
    public Detail deleteAllProjectsFromDetail(Long id) throws DetailNotFoundException {
        Project project = projectRepository.save(new Project("projetc", "a", 2, "d"));
        Detail detail = detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
        detailinfoRepository.save(new DetailInfo(30, detail, project));
        detail.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailinfoRepository.delete(detailInfo);
                });
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
    }

    @Override
    public boolean deleteAllDetails() {
        detailRepository.deleteAll();
        flushAllRepositories();
        entityManager.clear();
        /*
        // need to clear persistence context(cache 1st level),
        // because we want to take info from dataBase(not from cache)
        // because info, which getting from the cache isn't correct, because we update some info
        // and it isn't reflected in cache
        */
        return detailRepository.findAll().isEmpty();
    }

    @Override
    public Detail updateDetail(Long id, Detail detailNew) throws DetailNotFoundException {
        Detail detail=detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
        return detail.updateDetail(detailNew);
    }

    @Override
    public Detail findDetailById(Long id) throws DetailNotFoundException {
        return detailRepository.findById(id)
                .orElseThrow(DetailNotFoundException::new);
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
