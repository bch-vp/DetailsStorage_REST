package org.bch_vp.controller.details_controller.endpoint.details_id;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.bch_vp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
endPoint:
    ../details/{id}
 */
@RestController(value = "/details/id")
public class Controller {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @GetMapping("/fill")
    public ResponseEntity<?> getDetail() throws EntityNotFoundException, QuantityOfDetailsException {
        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
        Long idDetail_1=detailServiceImpl.saveEntity(detail_1).getId();

        Detail detail_2=new Detail("detail_2", "type","production",200, (double)40, "storage");
        Long idDetail_2=detailServiceImpl.saveEntity(detail_2).getId();

        Project project=new Project("prpject_1","type" , 1,"storage");
        Long idProject=projectServiceImpl.saveEntity(project).getId();

        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject);
        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject);
        return new ResponseEntity<>( HttpStatus.OK);

    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will return JSON(of this detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>((Detail)detailServiceImpl.findEntityById(id), HttpStatus.OK);

    }

    @PutMapping(value = "/details/{id}")
    public ResponseEntity<?> updateDetail (@PathVariable("id") Long id,
                                           @RequestBody (required = true) String jsonRequestBody) throws EntityNotFoundException, IOException {
        /*
        If everything is OK: API will update detail and return JSON(of this updated detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: @Valid detail, BAD_REQUEST(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */

        return new ResponseEntity<>(detailServiceImpl.updateEntity(id, jsonRequestBody), HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deleteDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will delete detail and return HttpStatus.OK
        In other cases API will send:
            - HttpStatus.NOT_MODIFIED(304)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailServiceImpl.deleteEntityById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
