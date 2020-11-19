package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailRepository;
import org.bch_vp.repository.ProjectRepository;
import org.bch_vp.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractStorageServiceImpl<Project, Detail, ProjectRepository, DetailRepository> implements ProjectService {

    private ProjectServiceImpl() {
        super(Project.class, Detail.class);
    }


//    @Autowired
//    private ProjectRepository projectRepository;
//    @Autowired
//    private DetailRepository detailRepository;
//    @Autowired
//    private DetailinfoRepository detailinfoRepository;
//
//    @PersistenceContext
//    private EntityManager entityManger;
//
//    @Override
//    public Project saveProject(Project project) {
//        return projectRepository.save(project);
//    }
//
//    @Override
//    public void deleteProjectById(Long id) {
//    //   entityManger.clear();
////get project
////        Optional<Project> projectOptional = projectRepository.findById(id);
////        if (!projectOptional.isPresent()) {
////            return;
////        }
////        Project project = projectOptional.get();
////        if(project.getDetailsInfo().size()==0){
////            projectRepository.delete(project);
////            return;
////        }
////        List<DetailInfo> list=detailinfoRepository.findAll();
//////get detailsInfo
////        List<DetailInfo> detailsInfo = project.getDetailsInfo();
////        List<Project> projects = projectRepository.findAll();
////        detailsInfo
////                .stream()
////                .forEach(detailInfo -> {
////                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
////                    detailInfo.getDetail().getDetailsInfo().remove(detailInfo);
////                    detailinfoRepository.delete(detailInfo);
////                });
////
////        projectRepository.deleteById(id);
//
//        Project project=projectRepository.findById(id).get();
//        project.getDetailsInfo()
//                .stream()
//                .forEach(detailInfo -> {
//                    detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
//                    detailinfoRepository.delete(detailInfo);
//                });
//        projectRepository.delete(project);
//        flushAllRepositories();
//    }
//
//
//    @Override
//    public List<Project> findAll() {
//        return projectRepository.findAll();
//    }
//
//    @Override
//    public Project findById(Long id) throws ProjectNotFoundException {
//        return projectRepository.findById(id)
//                .orElseThrow(ProjectNotFoundException::new);
//    }
//
//    @Override
//    public void deleteDetailInProject(Long idDetail, Long idProject) {
//        DetailInfo detailInfo=detailinfoRepository.findById(new IdDetailInfo(idDetail,idProject))
//                .orElseThrow(EntityNotFoundException::new);
//        detailInfo.getDetail().addAvailableDetails(detailInfo.getQuantityDetailsUsed());
//        detailinfoRepository.delete(detailInfo);
//        flushAllRepositories();
//    }

//    private void flushAllRepositories(){
//        detailinfoRepository.flush();
//        projectRepository.flush();
//        detailRepository.flush();
//    }
}
