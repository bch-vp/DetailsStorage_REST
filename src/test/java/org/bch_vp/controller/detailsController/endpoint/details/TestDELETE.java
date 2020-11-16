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

public class TestDELETE extends AbstractTest {

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void deleteAllDetails() throws Exception {
        Detail detail1 = new Detail("det-1","type","prod",23,2.0,"stor");
        Detail detail2 = new Detail("det-2","type","prod",23,2.0,"stor");
        detailServiceImpl.saveDetail(detail1);
        detailServiceImpl.saveDetail(detail2);

        String uri = "/details";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }
}
