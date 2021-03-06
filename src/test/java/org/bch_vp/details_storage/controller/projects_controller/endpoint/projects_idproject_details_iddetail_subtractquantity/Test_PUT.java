package org.bch_vp.details_storage.controller.projects_controller.endpoint.projects_idproject_details_iddetail_subtractquantity;


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
import org.springframework.transaction.annotation.Transactional;

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
    private String endPoint = "/projects/1/details/1/subtract-quantity";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    @Transactional
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
    @Transactional
    public void test1() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "30");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        Assert.assertEquals(Integer.valueOf(70), detailServiceImpl.findEntityById(1L).getQuantityOfAvailable());
        Assert.assertEquals(Integer.valueOf(0), detailInfoServiceImpl.findById(1L, 1L).getQuantityDetailsUsed());
        Integer quantityOfAllActual = detailInfoServiceImpl.findById(1L, 1L).getQuantityDetailsUsed()
                + detailInfoServiceImpl.findById(1L, 2L).getQuantityDetailsUsed();
        assertEquals(Integer.valueOf(30), quantityOfAllActual);
    }

    @Test
    @Transactional
    public void test2() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "15");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        Assert.assertEquals(Integer.valueOf(55), detailServiceImpl.findEntityById(1L).getQuantityOfAvailable());
        Assert.assertEquals(Integer.valueOf(15), detailInfoServiceImpl.findById(1L, 1L).getQuantityDetailsUsed());
        Integer quantityOfAllActual = detailInfoServiceImpl.findById(1L, 1L).getQuantityDetailsUsed()
                + detailInfoServiceImpl.findById(1L, 2L).getQuantityDetailsUsed();
        assertEquals(Integer.valueOf(45), quantityOfAllActual);
    }

    @Test
    @Transactional
    public void test3() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "31");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Transactional
    public void test4() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "41a");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Transactional
    public void test5() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "0");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Transactional
    public void test6() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "-3");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Transactional
    public void test7() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Transactional
    public void test8() throws Exception {
        Map<String, String> mapRequestBody=new HashMap<>();
        mapRequestBody.put("quantity", "30");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endPoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

}
