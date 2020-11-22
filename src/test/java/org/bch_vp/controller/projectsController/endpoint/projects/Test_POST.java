package org.bch_vp.controller.projectsController.endpoint.projects;

import org.bch_vp.controller.AbstractTest;
import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.IdNotValidException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class Test_POST extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    private String endPoint = "/projects";

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
    public void test1() throws Exception {
        Project project=new Project("prpject_3","type" , 1,"storage");
        String inputJson = super.mapToJson(project);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        Project projectFromResponse = super.mapFromJson(content, Project.class);
        assertEquals(project, projectFromResponse);
        assertEquals(3, projectServiceImpl.findAll().size());
    }

    @Test
    public void test2() throws Exception {
        Project project=new Project("prpject_3");
        String inputJson = super.mapToJson(project);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test3() throws Exception {
        Project project=new Project("prpject_3","type" , 1,"storage");
        project.setId(3L);
        String inputJson = super.mapToJson(project);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}
