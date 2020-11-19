package org.bch_vp.service.impl.detailServiceImplTest;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
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

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties ="classpath:application.properties")
public class TestDeleteDetail {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private EntityManager entityManager;

    @Before
    @Transactional
    public void fillDB() throws EntityNotFoundException, QuantityOfDetailsException {
        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
        Long idDetail_1=detailServiceImpl.saveEntity(detail_1).getId();

        Detail detail_2=new Detail("detail_2", "type","production",200, (double)40, "storage");
        Long idDetail_2=detailServiceImpl.saveEntity(detail_2).getId();

        Project project=new Project("prpject_1","type" , 1,"storage");
        Long idProject=projectServiceImpl.saveEntity(project).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject);
        //detailServiceImpl.deleteEntityById((long)1);
    }

    @Test
    @Transactional
    public void test1() throws EntityNotFoundException {
        //delete 1 detail from 2
        detailServiceImpl.deleteEntityById((long)1);

        entityManager.clear();

        List<Detail> detailList = detailServiceImpl.findAll();
        List<DetailInfo> detailsInfo =detailInfoServiceImpl.findAll();
        List<Project> projects =projectServiceImpl.findAll();

        Assert.assertEquals(1, detailServiceImpl.findAll().size());
        Assert.assertEquals(1, detailInfoServiceImpl.findAll().size());
        Assert.assertEquals(1, projectServiceImpl.findAll().size());
        Project project=projectServiceImpl.findEntityById((long)1);
        Assert.assertEquals(1, projectServiceImpl.findEntityById((long)1).getDetailsInfo().size());
    }
}
