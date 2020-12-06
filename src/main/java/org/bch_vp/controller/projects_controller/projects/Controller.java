package org.bch_vp.controller.projects_controller.projects;

import org.bch_vp.entity.exception_handler.entity.IdNotValidException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
endPoint:
   ../projects
*/
@RestController(value = "/projects")
public class Controller {
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects() {
        /*
        If everything is OK:
            - API will send array(which contains projects), HttpStatus.OK
            - If there are no projects - API will send JSON of empty array
        In other cases API will send:
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(projectServiceImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/projects")
    public ResponseEntity<?> createProject(@RequestBody @Valid Project project) throws IdNotValidException {
        /*
        If everything is OK: API will save project and return JSON(of this project), HttpStatus.CREATED(201)
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: IdNotValid , HttpStatus.BAD_REQUEST(400)
        */
        if(project.getId()!=null){ // rewrite
            throw new IdNotValidException();
        }
        project = (Project) projectServiceImpl.saveEntity(project);
        return project!=null
                ? new ResponseEntity<>(project,HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/projects")
    public ResponseEntity<?> deleteAllProjects() {
        /*
        if everything is okay: API will delete project and return HttpStatus.OK
        In other cases: API will send
            - HttpStatus.NOT_MODIFIED(304)
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return projectServiceImpl.deleteAllEntities()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
