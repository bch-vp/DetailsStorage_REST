package org.bch_vp.details_storage.controller.details_controller.endpoint.details;

import static org.junit.Assert.assertEquals;

import org.bch_vp.details_storage.controller.AbstractTest;
import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.exception_handler.exception.IdNotValidException;
import org.bch_vp.details_storage.service.impl.DetailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class Test_POST extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void addDetailWhichIsValidCorrect() throws Exception, IdNotValidException {
        String uri = "/details";
        Detail detail = new Detail("det","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail detailFromResponse = super.mapFromJson(content, Detail.class);
        assertEquals(detail, detailFromResponse);
        assertEquals(1, detailServiceImpl.findAll().size());
    }

    @Test
    public void addDetailWhichIsNotValidCorrect() throws Exception {
        String uri = "/details";
        Detail detail = new Detail("det");
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void addDetailWhichHasPresentedId() throws Exception {
        String uri = "/details";
        Detail detail = new Detail("det","type","prod",23,2.0,"stor");
        detail.setId(1L);
        String inputJson = super.mapToJson(detail);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}
