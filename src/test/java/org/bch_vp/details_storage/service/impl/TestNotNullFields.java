package org.bch_vp.details_storage.service.impl;

import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.entity.DetailInfo;
import org.bch_vp.details_storage.entity.Project;
import org.bch_vp.details_storage.exception_handler.exception.EntityNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.IdNotValidException;
import org.bch_vp.details_storage.exception_handler.exception.QuantityOfDetailsException;
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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties ="classpath:application.properties")
public class TestNotNullFields {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;



    @Before
    @Transactional
    public void fillDB() throws QuantityOfDetailsException, EntityNotFoundException, IdNotValidException {
        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
        Long idDetail_1=detailServiceImpl.saveEntity(detail_1).getId();

        Detail detail_2=new Detail("detail_2", "type","production",200, (double)40, "storage");
        Long idDetail_2=detailServiceImpl.saveEntity(detail_2).getId();

        Project project=new Project("prpject_1","type" , 1,"storage");
        Long idProject=projectServiceImpl.saveEntity(project).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject);
    }

    @Test
    @Transactional
    public void test(){
        List<DetailInfo> detailsInfo=detailInfoServiceImpl.findAll();
        detailsInfo // check that there aren't NULL fields(project and detail)
                .stream()
                .forEach(detailInfo ->{
                    Assert.assertNotNull(detailInfo.getDetail());
                    Assert.assertNotNull(detailInfo.getProject());
                    Assert.assertNotNull(detailInfo.getQuantityDetailsUsed());
                });
    }
}
