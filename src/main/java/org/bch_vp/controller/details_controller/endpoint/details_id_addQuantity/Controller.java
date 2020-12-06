package org.bch_vp.controller.details_controller.endpoint.details_id_addQuantity;

import org.bch_vp.entity.exception_handler.entity.EntityNotFoundException;
import org.bch_vp.entity.exception_handler.entity.NumberOfQuantityException;
import org.bch_vp.entity.exception_handler.entity.QuantityOfDetailsException;
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

@RestController(value = "details/id/addQuantity")
public class Controller {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @PutMapping(value = "details/{id}/addQuantity")
    public ResponseEntity<?> addQuantity(@PathVariable("id") Long id,
                                          @RequestBody(required = true) Integer quantity) throws EntityNotFoundException, IOException, QuantityOfDetailsException, NumberOfQuantityException {
        /*
        If everything is OK: API will add quantity of details and return JSON(of this updated detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: HttpMessageNotReadable(RequestBody isn't represented), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: NumberOfQuantity(Price is not correct), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.addQuantityOfDetails(id, quantity), HttpStatus.OK);
    }
}
