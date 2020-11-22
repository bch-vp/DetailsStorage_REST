package org.bch_vp.service.impl.abstractServiceImpl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.IdNotValidException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
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
public class TestFindInnerEntityFromEntity {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Before
    public void fillDataBase() throws QuantityOfDetailsException, EntityNotFoundException, IdNotValidException {
        Detail detail_1 = new Detail("detail_1", "type", "production", 100, (double) 40, "storage");
        Long idDetail_1 = detailServiceImpl.saveEntity(detail_1).getId();

        Detail detail_2 = new Detail("detail_2", "type", "production", 200, (double) 40, "storage");
        Long idDetail_2 = detailServiceImpl.saveEntity(detail_2).getId();

        Project project = new Project("prpject_1", "type", 1, "storage");
        Long idProject = projectServiceImpl.saveEntity(project).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject);

        Project project_2 = new Project("prpject_2", "type", 1, "storage");
        Long idProject_2 = projectServiceImpl.saveEntity(project_2).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject_2);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject_2);
    }

    @Test
    public void test1() throws EntityNotFoundException, DetailInfoNotFoundException {
        Project projectExpected = new Project("prpject_1", "type", 1, "storage");
        Project projectActual=detailServiceImpl.findInnerEntityFromEntity(1L,1L);
        Assert.assertEquals(projectExpected, projectActual);
    }

    @Test
    public void test2() throws EntityNotFoundException, DetailInfoNotFoundException {
        Project projectExpected = new Project("prpject_2", "type", 1, "storage");
        Project projectActual=detailServiceImpl.findInnerEntityFromEntity(1L,2L);
        Assert.assertEquals(projectExpected, projectActual);
    }

    @Test
    public void test3() throws EntityNotFoundException, DetailInfoNotFoundException {
        Detail detailExpected = new Detail("detail_1", "type", "production", 100, (double) 40, "storage");
        detailExpected.subtractAvailableDetails(60);
        Detail detailActual=projectServiceImpl.findInnerEntityFromEntity(1L,1L);
        Assert.assertEquals(detailExpected, detailActual);
    }

    @Test
    public void test4() throws EntityNotFoundException, DetailInfoNotFoundException {
        Detail detailExpected =  new Detail("detail_2", "type", "production", 200, (double) 40, "storage");
        detailExpected.subtractAvailableDetails(40);
        Detail detailActual=projectServiceImpl.findInnerEntityFromEntity(1L,2L);
        Assert.assertEquals(detailExpected, detailActual);
    }
}
