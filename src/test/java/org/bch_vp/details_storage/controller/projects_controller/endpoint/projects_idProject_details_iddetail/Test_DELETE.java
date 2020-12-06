package org.bch_vp.details_storage.controller.projects_controller.endpoint.projects_idProject_details_iddetail;


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
    public void testDeleteProjectFromDetailByCorrectIds() throws Exception {
        Detail detailExpected=new Detail("detail_2", "type","production",200, (double)40, "storage");
        String uri = "/projects/1/details/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        Assert.assertEquals(1, projectServiceImpl.findEntityById(1L).getDetailsInfo().size());
        Assert.assertEquals(3, detailInfoServiceImpl.findAll().size());
        Assert.assertEquals(2, detailServiceImpl.findEntityById(2L).getDetailsInfo().size());

    }

    @Test
    public void testDelete1ProjectFromDetailByIncorrectIdDetail() throws Exception {
        String uri = "/projects/3/details/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void testDelete2ProjectFromDetailByIncorrectIdDetail() throws Exception {
        String uri = "/projects/1a/details/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void testDelete1ProjectFromDetailByIncorrectIdProject() throws Exception {
        String uri = "/projects/1/details/3";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void testDelete2ProjectFromDetailByIncorrectIdProject() throws Exception {
        String uri = "/projects/1/details/1q";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }
}
