package org.bch_vp.details_storage.controller.details_controller.endpoint.details_id;

import org.bch_vp.details_storage.controller.AbstractTest;
import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.exception_handler.exception.EntityNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.IdNotValidException;
import org.bch_vp.details_storage.service.impl.DetailServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class Test_GET extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    public void fillDataBase() throws EntityNotFoundException, IdNotValidException {
        Detail detail1 = new Detail("det-1", "type", "prod", 23, 2.0, "stor");
        Detail detail2 = new Detail("det-2", "type", "prod", 23, 2.0, "stor");
        detailServiceImpl.saveEntity(detail1);
        detailServiceImpl.saveEntity(detail2);
       Detail detail= detailServiceImpl.findEntityById(1L);
    }

    @Test
    public void testGetDetailByCorrectId() throws Exception {
        Detail detailExpected = new Detail("det-1", "type", "prod", 23, 2.0, "stor");

        String uri = "/details/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail actualDetail = super.mapFromJson(content, Detail.class);
        assertEquals(detailExpected, actualDetail);
    }

    @Test
    public void test1GetDetailByIncorrectId() throws Exception {
        String uri = "/details/3";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void test2GetDetailByIncorrectId() throws Exception {
        String uri = "/details/-3";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void test3GetDetailByIncorrectId() throws Exception {
        String uri = "/details/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void test4GetDetailByIncorrectId() throws Exception {
        String uri = "/details/1a";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

}

