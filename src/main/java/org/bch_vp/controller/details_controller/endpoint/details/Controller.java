package org.bch_vp.controller.details_controller.endpoint.details;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.IdNotValidException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
endPoint:
   ../details
*/
@RestController(value = "/details")
public class Controller {
    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("/details")
    public ResponseEntity<?> getAllDetails() throws IdNotValidException, QuantityOfDetailsException, EntityNotFoundException {
//        Detail detail_1=new Detail("detail_1", "type","production",100, (double)40, "storage");
//        Long idDetail_1=detailServiceImpl.saveEntity(detail_1).getId();
//
//        Detail detail_2=new Detail("detail_2", "type","",200, (double)40, "storage");
//        Long idDetail_2=detailServiceImpl.saveEntity(detail_2).getId();
//
//        Project project=new Project("prpject_1","type" , 1,"storage");
//        Long idProject=projectServiceImpl.saveEntity(project).getId();
//
//        detailInfoServiceImpl.joinDetailAndProject(31, idDetail_1, idProject);
//        detailInfoServiceImpl.joinDetailAndProject(27, idDetail_2, idProject);
//
//        Project project_2=new Project("prpject_2","type" , 1,"storage");
//        Long idProject_2=projectServiceImpl.saveEntity(project_2).getId();
//
//        detailInfoServiceImpl.joinDetailAndProject(25,idDetail_1,idProject_2);
//        detailInfoServiceImpl.joinDetailAndProject(23,idDetail_2,idProject_2);
        /*
        If everything is OK:
            - API will send array(which contains details), HttpStatus.OK
            - If there are no Details - API will send JSON of empty array
        In other cases API will send:
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/details")
    public ResponseEntity<?> createDetail(@RequestBody @Valid Detail detail) throws IdNotValidException {
        /*
        If everything is OK: API will save detail and return JSON(of this detail), HttpStatus.CREATED(201)
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: IdNotValid , HttpStatus.BAD_REQUEST(400)
            - JSON about exception: MethodArgumentTypeMismatch(converting), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: HttpMessageNotReadable(RequestBody isn't represented), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: MethodArgumentNotValid(Some filed of entity in RequestBody isn't represented), HttpStatus.BAD_REQUEST(400)
        */
        detail = (Detail) detailServiceImpl.saveEntity(detail);
        return detail!=null
                ? new ResponseEntity<>(detail,HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/details")
    public ResponseEntity<?> deleteAllDetails() {
        /*
        if everything is okay:
            - API will delete all details and return HttpStatus.OK
        In other cases: API will send
            - HttpStatus.NOT_MODIFIED(304)
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailServiceImpl.deleteAllEntities()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
