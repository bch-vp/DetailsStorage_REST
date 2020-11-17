package org.bch_vp.controller.details_controller.endpoint.details_id;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
endPoint:
    ../details/{id}
 */
@RestController(value = "/details/id")
public class Controller {
    @Autowired
    private DetailServiceImpl detailServiceImpl;


    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will return JSON(of this detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>((Detail)detailServiceImpl.findEntityById(id), HttpStatus.OK);

    }

    @PutMapping(value = "/details/{id}")
    public ResponseEntity<?> updateDetail(@RequestBody @Valid Detail detail, @PathVariable("id") Long id) throws  EntityNotFoundException{
        /*
        If everything is OK: API will update detail and return JSON(of this updated detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: @Valid detail, BAD_REQUEST(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.updateEntity(id, detail), HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deleteDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will delete detail and return HttpStatus.OK
        In other cases API will send:
            - HttpStatus.NOT_MODIFIED(304)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailServiceImpl.deleteEntityById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
