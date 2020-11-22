package org.bch_vp.controller.details_controller.endpoint.details_idDetail_projects_idProject;

import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

/*
endPoint:
    ../details/{idDetail}/projects/{idProject}
 */
@RestController(value = "/details/idDetail/projects/idProject")
public class Controller {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> getProjectFromDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject) throws EntityNotFoundException, DetailInfoNotFoundException {
        /*
        If everything is OK: API will send JSON of project from detail and HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        Project project = (Project) detailServiceImpl.findInnerEntityFromEntity(idDetail,idProject);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> addProjectToDetail(@RequestBody String jsonQuantityOfDetails,
                                                @PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject) throws QuantityOfDetailsException, EntityNotFoundException, IOException {
        /*
        Method where you can add project to detail(which already exists) with quantity of detail...
        If everything is OK: API will send HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: AlreadyHasRelations, HttpStatus.NOT_FOUND(400). It means, that detail alredy contains this project
            - jSON about exception: QuantityOfDetails, HttpStatus.NOT_FOUND(400)
            - JSON about exception: HttpMessageNotReadableException(RequestBody isn't represented), HttpStatus.BAD_REQUEST(400)
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

    @DeleteMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> deleteProjectFromDetail(@PathVariable(value = "idDetail") Long idDetail,
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
        return detailServiceImpl.deleteInnerEntityFromEntity(idDetail, idProject)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
