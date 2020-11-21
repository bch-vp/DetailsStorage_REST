package org.bch_vp.controller.projectsController.endpoint.projects_id;

import org.bch_vp.controller.AbstractTest;
import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Test_PUT extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    private String endPoint = "/projects/1";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    public void fillDataBase() throws QuantityOfDetailsException, EntityNotFoundException {
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
    public void test1() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        Project project = new Project("pr-1", "type", 1, "storage");
        mapRequestBody.put("projectName", "pr-1");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Project projectMapFromResponse = super.mapFromJson(content, Project.class);
        assertEquals(project, projectMapFromResponse);
    }


    @Test
    public void test2() throws Exception {
        Project project = new Project("prpject_3");
        String inputJson = super.mapToJson(project);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void test3() throws Exception {
        Project project = new Project("prpject_3", "type", 1, "storage");
        String inputJson = super.mapToJson(project);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1a")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test4() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "pr-1");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test5() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "1a");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test6() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "0");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test7() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "-2");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test8() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "2.8");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}