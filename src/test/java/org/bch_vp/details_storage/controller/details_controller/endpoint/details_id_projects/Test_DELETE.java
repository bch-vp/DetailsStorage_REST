package org.bch_vp.details_storage.controller.details_controller.endpoint.details_id_projects;

import org.bch_vp.details_storage.controller.AbstractTest;
import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.entity.Project;
import org.bch_vp.details_storage.exception_handler.exception.EntityNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.IdNotValidException;
import org.bch_vp.details_storage.exception_handler.exception.QuantityOfDetailsException;
import org.bch_vp.details_storage.service.impl.DetailInfoServiceImpl;
import org.bch_vp.details_storage.service.impl.DetailServiceImpl;
import org.bch_vp.details_storage.service.impl.ProjectServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class Test_DELETE extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    public void fillDataBase() throws QuantityOfDetailsException, EntityNotFoundException, IdNotValidException {
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
    public void testDeleteDetailByCorrectId() throws Exception {
        String uri = "/details/1/projects";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }

    @Test
    public void test1DeleteDetailByIncorrectId() throws Exception {
        String uri = "/details/0/projects";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void test2DeleteDetailByIncorrectId() throws Exception {
        String uri = "/details/-3/projects";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void test3DeleteDetailByIncorrectId() throws Exception {
        String uri = "/details/2a/projects";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }
}
