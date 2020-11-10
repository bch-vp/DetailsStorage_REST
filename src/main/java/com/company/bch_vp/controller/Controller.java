package com.company.bch_vp.controller;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.DetailInfo;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.service.impl.DetailInfoServiceImpl;
import com.company.bch_vp.service.impl.DetailServiceImpl;
import com.company.bch_vp.service.impl.ProjectServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//ObjectMapper objectMapper=new ObjectMapper();
//        ObjectNode objectNode=objectMapper.createObjectNode();
//        objectNode.put("key","value");
//        objectNode.putPOJO("detailInfo",detailInfoServiceImpl.findById((long)1,(long)1));
//        result=objectNode.toString();

@RestController
public class Controller {

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @GetMapping(value = "/details")
    public ResponseEntity<?> getDetails(){
        List<Detail> details = detailServiceImpl.findAll();
        return details!= null ?
                new ResponseEntity<>(details, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/details", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addDetail(@RequestBody Detail detail){
        detailServiceImpl.saveDetail(detail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
