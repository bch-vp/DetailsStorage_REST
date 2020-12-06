package org.bch_vp.controller.projects_controller.projects_idProject_details_idDetail_addQuantity;

import org.bch_vp.entity.exception_handler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.exception_handler.entity.EntityNotFoundException;
import org.bch_vp.entity.exception_handler.entity.QuantityOfDetailsException;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/projects/{idProject}/details/{idDetail}/add-quantity")
public class Controller {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @PutMapping(value = "/projects/{idProject}/details/{idDetail}/add-quantity")
    public ResponseEntity<?> addQuantityOfDetails(@PathVariable("idDetail") Long idDetail,
                                                  @PathVariable("idProject") Long idProject,
                                                  @RequestBody(required = true) String jsonQuantityRequestBody) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException {
        /*
        If everything is OK:
            - API will updated quantity of details in project, HttpStatus.OK
        In other cases API will send:
            In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailInfoServiceImpl.addQuantityOfDetailsInProject(jsonQuantityRequestBody, idDetail, idProject)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
