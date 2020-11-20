package org.bch_vp.controller.projects_controller.projects_id_details;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/*
endPoint:
    ../projects/{id}/details
*/
@RestController(value = "/projects/id/details")
public class Controller {
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("/projects/{id}/details")
    public ResponseEntity<?> getDetailFromProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send JSON of array(also it can be empty array), which contains details from project with {id}, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        List<Detail> projects=projectServiceImpl
                .findEntityById(id)
                .getDetailsInfo()
                .stream()
                .map(DetailInfo::getDetail)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @DeleteMapping("/projects/{id}/details")
    public ResponseEntity<?> deleteDetailFromProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return projectServiceImpl.deleteAllInnerEntitiesFromEntity(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
