package org.bch_vp.controller.projects_controller.projects_idProject_details_idDetail_addQuantity;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
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

@RestController("v")
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
//        Detail detail_1 = new Detail("detail_1", "type", "production", 100, (double) 40, "storage");
//        Long idDetail_1 = detailServiceImpl.saveEntity(detail_1).getId();
//
//        Detail detail_2 = new Detail("detail_2", "type", "production", 200, (double) 40, "storage");
//        Long idDetail_2 = detailServiceImpl.saveEntity(detail_2).getId();
//
//        Project project = new Project("prpject_1", "type", 1, "storage");
//        Long idProject1 = projectServiceImpl.saveEntity(project).getId();
//
//        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject1);
//        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject1);
//
//        Project project_2 = new Project("prpject_2", "type", 1, "storage");
//        Long idProject_2 = projectServiceImpl.saveEntity(project_2).getId();
//
//        detailInfoServiceImpl.joinDetailAndProject(30, idDetail_1, idProject_2);
//        detailInfoServiceImpl.joinDetailAndProject(20, idDetail_2, idProject_2);
        return detailInfoServiceImpl.addQuantityOfDetailsInProject(jsonQuantityRequestBody, idDetail, idProject)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
