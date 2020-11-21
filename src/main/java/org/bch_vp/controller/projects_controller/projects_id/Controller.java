package org.bch_vp.controller.projects_controller.projects_id;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.PriceNotCorrectException;
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
    ../projects/{id}
 */
@RestController(value = "/projects/id")
public class Controller {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @GetMapping("/projects/{id}")
    public ResponseEntity<?> getProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will return JSON(of this project), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>((Project)projectServiceImpl.findEntityById(id), HttpStatus.OK);

    }

    @PutMapping(value = "/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("id") Long id,
                                           @RequestBody (required = true) String jsonRequestBody) throws EntityNotFoundException, IOException, QuantityOfDetailsException, PriceNotCorrectException {
        /*
        If everything is OK: API will update project and return JSON(of this updated project), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: @Valid project, BAD_REQUEST(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(projectServiceImpl.updateEntity(id, jsonRequestBody), HttpStatus.OK);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will delete project and return HttpStatus.OK
        In other cases API will send:
            - HttpStatus.NOT_MODIFIED(304)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return projectServiceImpl.deleteEntityById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

