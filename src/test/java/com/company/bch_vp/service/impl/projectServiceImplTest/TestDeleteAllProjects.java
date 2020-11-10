package com.company.bch_vp.service.impl.projectServiceImplTest;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.service.impl.DetailInfoServiceImpl;
import com.company.bch_vp.service.impl.DetailServiceImpl;
import com.company.bch_vp.service.impl.ProjectServiceImpl;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties ="classpath:application.properties")
public class TestDeleteAllProjects {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Before
    @Transactional
    public void fillDB(){
        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
        Long idDetail_1=detailServiceImpl.saveDetail(detail_1).getId();

        Detail detail_2=new Detail("detail_2", "type","production",200, (double)40, "storage");
        Long idDetail_2=detailServiceImpl.saveDetail(detail_2).getId();

        Project project=new Project("prpject_1","type" , 1,"storage");
        Long idProject=projectServiceImpl.saveProject(project).getId();

        detailInfoServiceImpl.addDetail(30, idDetail_1, idProject);
        detailInfoServiceImpl.addDetail(20, idDetail_2, idProject);

        Project project_2=new Project("prpject_2","type" , 1,"storage");
        Long idProject_2=projectServiceImpl.saveProject(project_2).getId();

    }

    @Test
    @Transactional
    public void test(){
        //gonna delete all projects from DB
        projectServiceImpl.findAll()
                .stream()
                .forEach(project -> {
                    projectServiceImpl.deleteProjectById(project.getId());
                });
        Assert.assertEquals(0,projectServiceImpl.findAll().size());
        Assert.assertEquals(0,detailInfoServiceImpl.findAll().size());
        Assert.assertEquals(2,detailServiceImpl.findAll().size());
    }
}
