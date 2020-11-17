package org.bch_vp.controller.details_controller.endpoint.details_idDetail_projects_idProject;

import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
endPoint:
    ../details/{idDetail}/projects/{idProject}
 */
@RestController(value = "/details/idDetail/projects/idProject")
public class Controller {

    @Autowired
    private DetailServiceImpl detailServiceImpl;

    @GetMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> getProjectFromDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject) throws EntityNotFoundException, DetailInfoNotFoundException {
        /*
        If everything is OK: API will send JSON of project and HttpStatus.OK
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
    public ResponseEntity<?> addProjectToDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject){
        return null;
    }

    @PutMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> updateProjectFromDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject){
        return null;
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
