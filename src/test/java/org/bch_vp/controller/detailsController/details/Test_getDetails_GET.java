package org.bch_vp.controller.detailsController.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.bch_vp.controller.AbstractTest;
import org.bch_vp.entity.Detail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

public class Test_getDetails_GET extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    public void fil() throws Exception {
        String uri = "/details";
        Detail product = new Detail("det","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
         mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, inputJson);
    }

    @Test
    public void getProductsList() throws Exception {
        String uri = "/details";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail[] productlist = super.mapFromJson(content, Detail[].class);
        assertTrue(productlist.length == 0);
    }

    @Test
    public void createProduct() throws Exception {
        String uri = "/details";
        Detail product = new Detail("det","type","prod",23,2.0,"stor");
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, inputJson);
    }
}
