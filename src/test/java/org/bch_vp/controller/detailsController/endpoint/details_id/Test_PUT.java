package org.bch_vp.controller.detailsController.endpoint.details_id;

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

public class Test_PUT extends AbstractTest{

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Before
    public void fillDataBase() throws EntityNotFoundException, QuantityOfDetailsException {
        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
        Long idDetail_1=detailServiceImpl.saveEntity(detail_1).getId();

        Detail detail_2=new Detail("detail_2", "type","production",200, (double)40, "storage");
        Long idDetail_2=detailServiceImpl.saveEntity(detail_2).getId();

        Project project=new Project("prpject_1","type" , 1,"storage");
        Long idProject=projectServiceImpl.saveEntity(project).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject);
    }

    @Test
    public void testUpdateDetailByCorrectId() throws Exception {
        String uri = "/details/1";
        Map<String, String> mapRequestBody=new HashMap<>();
        Detail detail = new Detail("det","type","prod", 100,2.0,"stor");
        mapRequestBody.put("detailName", "det");
        mapRequestBody.put("type", "type");
        mapRequestBody.put("production", "prod");
        mapRequestBody.put("price", "2.0");
        mapRequestBody.put("storage", "stor");

        String inputJson = super.mapToJson(mapRequestBody);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Detail detailMapFromResponse = super.mapFromJson(content, Detail.class);
        assertEquals(detail, detailMapFromResponse);
    }
}
