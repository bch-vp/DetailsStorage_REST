package com.company.bch_vp.controller;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.DetailInfo;
import com.company.bch_vp.entity.ExceptionHandler.EntityNotFoundException;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.service.impl.DetailInfoServiceImpl;
import com.company.bch_vp.service.impl.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//ObjectMapper objectMapper=new ObjectMapper();
//        ObjectNode objectNode=objectMapper.createObjectNode();
//        objectNode.put("key","value");
//        objectNode.putPOJO("detailInfo",detailInfoServiceImpl.findById((long)1,(long)1));
//        result=objectNode.toString();

@RestController
public class DetailsController {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceIml;

/*
endPoint:
../details
*/
    @GetMapping("/details")
    public ResponseEntity<?> getDetails() {
        List<Detail> details = detailServiceImpl.findAll();
        return details != null && !details.isEmpty()
                ? new ResponseEntity<>(details, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/details")
    public ResponseEntity<?> createDetail(@RequestBody @Valid Detail detail) {
        return Optional.ofNullable(detailServiceImpl.saveDetail(detail))
                .map(detail1 -> {return new ResponseEntity<>(HttpStatus.CREATED);})
                .orElseGet(()->{return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);});
    }

    @DeleteMapping("/details")
    public ResponseEntity<?> deleteAllDetails() {
        return detailServiceImpl.deleteAllDetails()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

/*
endPoint:
../details/{id}
*/
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        return Optional.ofNullable(detailServiceImpl.findDetailById(id))
                .map(detail -> {return new ResponseEntity<>(detail, HttpStatus.OK);})
                .orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping(value = "/details/{id}")
    public ResponseEntity<?> updateDetail(@RequestBody Detail detail, @PathVariable("id") Long id) {
        return detailServiceImpl.updateDetail(id, detail)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deleteDetail(@PathVariable("id") Long id) {
        return detailServiceImpl.deleteDetailById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

/*
endPoint:
../details/{id}/projects
 */
    @GetMapping("/details/{id}/projects")
    public ResponseEntity<?> getProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        List<DetailInfo> detailsInfo = Optional.ofNullable(detailServiceImpl.findDetailById(id))
                .map(detail -> {return detail.getDetailsInfo();})
                .orElseThrow(EntityNotFoundException::new);
        List<Project> projects=detailsInfo
                .stream()
                .map(DetailInfo::getProject)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}/projects")
    public ResponseEntity<?> deleteProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
       return detailServiceImpl.deleteAllProjectsFromDetail(id)
               ? new ResponseEntity<>(HttpStatus.OK)
               : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

/*
endPoint:
../details/{idDetail}/projects/{idProject}
 */
    @PostMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> addProjectToDetail(@PathVariable("idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject){
       Optional.ofNullable(detailServiceImpl.findDetailById(idDetail));
       return null;
    }
}
