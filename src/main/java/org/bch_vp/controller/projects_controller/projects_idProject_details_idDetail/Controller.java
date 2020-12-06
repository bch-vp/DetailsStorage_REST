package org.bch_vp.controller.projects_controller.projects_idProject_details_idDetail;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.exception_handler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.exception_handler.entity.EntityNotFoundException;
import org.bch_vp.entity.exception_handler.entity.QuantityOfDetailsException;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.bch_vp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

/*
endPoint:
    ../projects/{idProject}/details/{idDetail}
 */
@RestController(value = "/projects/idProject/details/idDetail")
public class Controller {

    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("/projects/{idProject}/details/{idDetail}")
    public ResponseEntity<?> getDetailFromProject(@PathVariable(value = "idDetail") Long idDetail,
                                                  @PathVariable("idProject") Long idProject) throws EntityNotFoundException, DetailInfoNotFoundException {
        /*
        If everything is OK: API will send JSON of detail from project and HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        Detail detail = (Detail) projectServiceImpl.findInnerEntityFromEntity(idProject,idDetail);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @PostMapping("/projects/{idProject}/details/{idDetail}")
    public ResponseEntity<?> addDetailToProject(@RequestBody String jsonQuantityOfDetails,
                                                @PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject) throws QuantityOfDetailsException, EntityNotFoundException, IOException {
        /*
        Method where you can add detail to project with quantity of details(quantity > 0 && quantity < quantityOfAvailable)
        If everything is OK: API will send HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: AlreadyHasRelations, HttpStatus.NOT_FOUND(400). It means, that detail alredy contains this project
            - jSON about exception: QuantityOfDetails, HttpStatus.NOT_FOUND(400)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        HashMap mapRequestBody= JsonUtil.mapFromJson(jsonQuantityOfDetails, HashMap.class);
        String quantityString = (String) mapRequestBody.get("quantity");
        if(!quantityString.matches("^[0-9]+$")){
            throw new QuantityOfDetailsException(quantityString);
        }
        return detailInfoServiceImpl.joinDetailAndProject(Integer.valueOf(quantityString), idDetail, idProject)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/projects/{idProject}/details/{idDetail}")
    public ResponseEntity<?> deleteDetailFromProject(@PathVariable(value = "idDetail") Long idDetail,
                                                     @PathVariable("idProject") Long idProject) throws EntityNotFoundException, DetailInfoNotFoundException {
        /*
        If everything is OK: API will send HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return projectServiceImpl.deleteInnerEntityFromEntity(idProject, idDetail)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

