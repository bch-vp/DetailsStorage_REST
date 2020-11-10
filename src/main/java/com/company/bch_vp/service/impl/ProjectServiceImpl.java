package com.company.bch_vp.service.impl;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.DetailInfo;
import com.company.bch_vp.entity.IdDetailInfo;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.repository.DetailRepository;
import com.company.bch_vp.repository.DetailinfoRepository;
import com.company.bch_vp.repository.ProjectRepository;
import com.company.bch_vp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DetailRepository detailRepository;
    @Autowired
    private DetailinfoRepository detailinfoRepository;

    @PersistenceContext
    private EntityManager entityManger;

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProjectById(Long id) {
    //   entityManger.clear();
//get project
//        Optional<Project> projectOptional = projectRepository.findById(id);
//        if (!projectOptional.isPresent()) {
//            return;
//        }
//        Project project = projectOptional.get();
//        if(project.getDetailsInfo().size()==0){
//            projectRepository.delete(project);
//            return;
//        }
//        List<DetailInfo> list=detailinfoRepository.findAll();
////get detailsInfo
//        List<DetailInfo> detailsInfo = project.getDetailsInfo();
//        List<Project> projects = projectRepository.findAll();
//        detailsInfo
//                .stream()
//                .forEach(detailInfo -> {
//                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
//                    detailInfo.getDetail().getDetailsInfo().remove(detailInfo);
//                    detailinfoRepository.delete(detailInfo);
//                });
//
//        projectRepository.deleteById(id);

        Project project=projectRepository.findById(id).get();
        project.getDetailsInfo()
                .stream()
                .forEach(detailInfo -> {
                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
                    detailinfoRepository.delete(detailInfo);
                });
        projectRepository.delete(project);
        flushAllRepositories();
    }


    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public void deleteDetailInProject(Long idDetail, Long idProject) {
        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject));
        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
        detailinfoRepository.delete(detailInfo);
        flushAllRepositories();
    }

    private void flushAllRepositories(){
        detailinfoRepository.flush();
        projectRepository.flush();
        detailRepository.flush();
    }
}
