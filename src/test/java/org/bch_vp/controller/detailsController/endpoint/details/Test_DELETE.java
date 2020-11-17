package org.bch_vp.controller.detailsController.endpoint.details;

import org.bch_vp.controller.AbstractTest;
import org.bch_vp.entity.Detail;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class Test_DELETE extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    public void fillDataBase(){
        Detail detail1 = new Detail("det-1", "type", "prod", 23, 2.0, "stor");
        Detail detail2 = new Detail("det-2", "type", "prod", 23, 2.0, "stor");
        detailServiceImpl.saveEntity(detail1);
        detailServiceImpl.saveEntity(detail2);
    }

    @Test
    public void testUpdateDetailWithCorrectRequestBody() throws Exception {
        String uri = "/details/1";
        Detail detail = new Detail("det","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail detailFromResponse = super.mapFromJson(content, Detail.class);
        assertEquals(detail, detailFromResponse);
    }

    @Test
    public void test1UpdateDetailWithNotCorrectRequestBody() throws Exception {
        String uri = "/details/1";
        Detail detail = new Detail("","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test2UpdateDetailWithNotCorrectRequestBody() throws Exception {
        String uri = "/details/1";
        Detail detail = new Detail("det");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void test3UpdateDetailWithNotCorrectRequestBody() throws Exception {
        String uri = "/details/3";
        Detail detail = new Detail("det","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(404, status);
    }

    @Test
    public void test4UpdateDetailWithNotCorrectRequestBody() throws Exception {
        String uri = "/details/3a";
        Detail detail = new Detail("det","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(400, status);
    }
}
