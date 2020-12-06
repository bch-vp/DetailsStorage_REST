package org.bch_vp.controller.detailsController.endpoint.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.bch_vp.controller.AbstractTest;
import org.bch_vp.entity.Detail;
import org.bch_vp.entity.exception_handler.entity.IdNotValidException;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class Test_GET extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @Test
    public void getDetailsListFromDBWhereTwoDetails() throws Exception, IdNotValidException {
        Detail detail1 = new Detail("det-1","type","prod",23,2.0,"stor");
        Detail detail2 = new Detail("det-2","type","prod",23,2.0,"stor");
        detailServiceImpl.saveEntity(detail1);
        detailServiceImpl.saveEntity(detail2);

        String uri = "/details";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail[] productlist = super.mapFromJson(content, Detail[].class);
        assertEquals(2, productlist.length);
    }

    @Test
    public void getDetailsListFromDBWhereZeroDetails() throws Exception {
        String uri = "/details";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail[] productlist = super.mapFromJson(content, Detail[].class);
        assertEquals(0, productlist.length);
    }

    @Test
    public void getDetailsListFromDBWhereOneDetails() throws Exception {
        Detail detail = new Detail("det-1","type","prod",23,2.0,"stor");
        detailServiceImpl.saveEntity(detail);

        String uri = "/details";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail[] productlist = super.mapFromJson(content, Detail[].class);
        assertEquals(1, productlist.length);
    }

}
