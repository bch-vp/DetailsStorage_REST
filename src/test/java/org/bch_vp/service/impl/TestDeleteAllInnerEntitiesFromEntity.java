package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
@Transactional
public class TestDeleteAllInnerEntitiesFromEntity {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Before
    public void fillDataBase() throws QuantityOfDetailsException, EntityNotFoundException {
        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
        Long idDetail_1=detailServiceImpl.saveEntity(detail_1).getId();

        Detail detail_2=new Detail("detail_2", "type","production",200, (double)40, "storage");
        Long idDetail_2=detailServiceImpl.saveEntity(detail_2).getId();

        Project project=new Project("prpject_1","type" , 1,"storage");
        Long idProject=projectServiceImpl.saveEntity(project).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject);

        Project project_2=new Project("prpject_2","type" , 1,"storage");
        Long idProject_2=projectServiceImpl.saveEntity(project_2).getId();

        detailInfoServiceImpl.joinDetailAndProject(30,idDetail_1,idProject_2);
        detailInfoServiceImpl.joinDetailAndProject(20,idDetail_2,idProject_2);
    }

    @Test
    public void test1() throws EntityNotFoundException {
        Long idDetail=1L;
        detailServiceImpl.deleteAllInnerEntitiesFromEntity(idDetail);
        Assert.assertEquals(0, detailServiceImpl.findEntityById(idDetail).getDetailsInfo().size());
        Assert.assertEquals(Integer.valueOf(100), detailServiceImpl.findEntityById(idDetail).getQuantityOfAvailable());
        Assert.assertEquals(Integer.valueOf(160), detailServiceImpl.findEntityById(2L).getQuantityOfAvailable());
    }

    @Test
    public void test2() throws EntityNotFoundException {
        Long idDetail=2L;
        detailServiceImpl.deleteAllInnerEntitiesFromEntity(idDetail);
        Assert.assertEquals(0, detailServiceImpl.findEntityById(idDetail).getDetailsInfo().size());
        Assert.assertEquals(Integer.valueOf(200), detailServiceImpl.findEntityById(idDetail).getQuantityOfAvailable());
        Assert.assertEquals(Integer.valueOf(40), detailServiceImpl.findEntityById(1L).getQuantityOfAvailable());
    }

    @Test
    public void test3() throws EntityNotFoundException {
        Long idProject=1L;
        projectServiceImpl.deleteAllInnerEntitiesFromEntity(idProject);
        Assert.assertEquals(0, projectServiceImpl.findEntityById(idProject).getDetailsInfo().size());
        Assert.assertEquals(Integer.valueOf(70), detailServiceImpl.findEntityById(1L).getQuantityOfAvailable());
        Assert.assertEquals(Integer.valueOf(180), detailServiceImpl.findEntityById(2L).getQuantityOfAvailable());
    }

    @Test
    public void test4() throws EntityNotFoundException {
        Long idProject=2L;
        projectServiceImpl.deleteAllInnerEntitiesFromEntity(idProject);
        Assert.assertEquals(0, projectServiceImpl.findEntityById(idProject).getDetailsInfo().size());
        Assert.assertEquals(Integer.valueOf(70), detailServiceImpl.findEntityById(1L).getQuantityOfAvailable());
        Assert.assertEquals(Integer.valueOf(180), detailServiceImpl.findEntityById(2L).getQuantityOfAvailable());
    }
}
