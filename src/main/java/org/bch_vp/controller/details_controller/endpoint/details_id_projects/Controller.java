package org.bch_vp.controller.details_controller.endpoint.details_id_projects;

import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
endPoint:
    ../details/{id}/projects
*/
@RestController(value = "/details/id/projects")
public class Controller {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceIml;

    @GetMapping("/details/{id}/projects")
    public ResponseEntity<?> getProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send JSON of array(also it can be empty array), which contains projects from detail with {id}, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        List<Project> projects=detailServiceImpl
                .findEntityById(id)
                .getDetailsInfo()
                .stream()
                .map(DetailInfo::getProject)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}/projects")
    public ResponseEntity<?> deleteProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailServiceImpl.deleteAllInnerEntitiesFromEntity(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
