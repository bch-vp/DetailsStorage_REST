package org.bch_vp.controller.details_controller.endpoint.details_idDetail_projects_idProject;

import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
endPoint:
    ../details/{idDetail}/projects/{idProject}
 */
@RestController(value = "/details/idDetail/projects/idProject")
public class Controller {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceIml;

    @PostMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> addProjectToDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject){
        return null;
    }
}
