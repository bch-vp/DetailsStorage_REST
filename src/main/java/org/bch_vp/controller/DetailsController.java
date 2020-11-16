package org.bch_vp.controller;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.IdNotValid;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        /*
        If everything is OK:
            - API will send array(which contains details), HttpStatus.OK
            - If there are no Details - API will send JSON of empty array
        In other cases API will send:
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping("/details")
    public ResponseEntity<?> createDetail(@RequestBody @Valid Detail detail) throws IdNotValid {
        /*
        If everything is OK: API will save detail and return JSON(of this detail), HttpStatus.OK
        In other cases: API will send HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        if(detail.getId()!=null){
            throw new IdNotValid();
        }
        detail =detailServiceImpl.saveDetail(detail);
        return detail!=null
                ? new ResponseEntity<>(detail,HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/details")
    public ResponseEntity<?> deleteAllDetails() {
        /*
        if everything is okay: API will delete detail and return HttpStatus.OK
        In other cases: API will send INTERNAL_SERVER_ERROR(500)
        */
        return detailServiceImpl.deleteAllDetails()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
/*
endPoint:
../details/{id}
*/
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) throws DetailNotFoundException {
        /*
        If everything is OK: API will return JSON(of this detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.findDetailById(id), HttpStatus.OK);

    }

    @PutMapping(value = "/details/{id}")
    public ResponseEntity<?> updateDetail(@RequestBody @Valid Detail detail, @PathVariable("id") Long id) throws DetailNotFoundException {
        /*
        If everything is OK: API will update detail and return JSON(of this updated detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: @Valid detail, BAD_REQUEST(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.updateDetail(id, detail),HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deleteDetail(@PathVariable("id") Long id) throws DetailNotFoundException {
        /*
        If everything is OK: API will delete detail and return HttpStatus.OK
        In other cases API will send:
            - HttpStatus.NOT_MODIFIED(304)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailServiceImpl.deleteDetailById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
/*
endPoint:
../details/{id}/projects
 */
    @GetMapping("/details/{id}/projects")
    public ResponseEntity<?> getProjectsFromDetail(@PathVariable("id") Long id) throws DetailNotFoundException {
        /*
        If everything is OK: API will send JSON of array(also it can be empty array), which contains projects from detail with {id}, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        List<Project> projects=detailServiceImpl
                .findDetailById(id)
                .getDetailsInfo()
                .stream()
                .map(DetailInfo::getProject)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}/projects")
    public ResponseEntity<?> deleteProjectsFromDetail(@PathVariable("id") Long id) throws DetailNotFoundException {
        /*
        If everything is OK: API will send JSON of detail with empty array of projects, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        Detail detail = detailServiceImpl.deleteAllProjectsFromDetail(id);
        return detail.getDetailsInfo().isEmpty()
                ? new ResponseEntity<>(detail, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

/*
endPoint:
../details/{idDetail}/projects/{idProject}
 */
    @PostMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> addProjectToDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject){
       return null;
    }
}
