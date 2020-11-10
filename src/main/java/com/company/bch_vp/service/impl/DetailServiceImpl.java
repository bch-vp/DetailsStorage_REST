package com.company.bch_vp.service.impl;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.DetailInfo;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.repository.DetailRepository;
import com.company.bch_vp.repository.DetailinfoRepository;
import com.company.bch_vp.repository.ProjectRepository;
import com.company.bch_vp.service.DetailInfoService;
import com.company.bch_vp.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailinfoRepository detailinfoRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DetailRepository detailRepository;

    @Override
    public Detail saveDetail(Detail detail) {
       return detailRepository.save(detail);
    }

    @Override
    public void  deleteDetailById(Long id) {
        /*
        if u gonna delete detail, u must delete all projects which contain this detail
           BE SO CAREFUL WITH THAT !!!
        */

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


        Detail detail=detailRepository.findById(id).get();
        detail.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailinfoRepository.delete(detailInfo);
                });
        detailRepository.delete(detail);
        flushAllRepositories();
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
    public Detail findDetailById(Long id) {
        return detailRepository.findById(id).get();
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
