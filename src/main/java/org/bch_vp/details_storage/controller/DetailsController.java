package org.bch_vp.details_storage.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bch_vp.details_storage.entity.DetailInfo;
import org.bch_vp.details_storage.entity.Project;
import org.bch_vp.details_storage.exception_handler.exception.*;
import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.service.impl.DetailInfoServiceImpl;
import org.bch_vp.details_storage.service.impl.DetailServiceImpl;
import org.bch_vp.details_storage.service.impl.ProjectServiceImpl;
import org.bch_vp.details_storage.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DetailsController {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("details")
    public ResponseEntity<?> getAllDetails() {
        /*
        If everything is OK:
            - API will send array(which contains details), HttpStatus.OK
            - If there are no Details - API will send JSON of empty array
        In other cases API will send:
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping("details")
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

    @DeleteMapping("details")
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

    @GetMapping("details/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will return JSON(of this detail), HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>((Detail)detailServiceImpl.findEntityById(id), HttpStatus.OK);

    }

    @PutMapping("details/{id}")
    public ResponseEntity<?> updateDetail(@PathVariable("id") Long id,
                                          @RequestBody (required = true) String jsonRequestBody)
            throws EntityNotFoundException, QuantityOfDetailsException, IOException, NumberOfQuantityException {
        /*
        If everything is OK: API will update detail and return JSON(of this updated detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: HttpMessageNotReadable(RequestBody isn't represented), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: NumberOfQuantity(Price is not correct), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.updateEntity(id, jsonRequestBody), HttpStatus.OK);
    }

    @DeleteMapping("details/{id}")
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

    @GetMapping("details/{id}/projects")
    public ResponseEntity<?> getAllProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send JSON of array(also it can be empty array), which contains projects from detail with {id}, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        List<DetailInfo> detailsInfo = detailServiceImpl
                .findEntityById(id)
                .getDetailsInfo();
        List<Project> projects = detailsInfo
                .stream()
                .map(DetailInfo::getProject)
                .collect(Collectors.toList());

        if (!projects.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.valueToTree(projects);

            int i = 0;
            for (JsonNode objNode : node) {
                ((ObjectNode) objNode).put("quantityInUsed", detailsInfo.get(i).getQuantityDetailsUsed());
                i++;
            }
            String str = node.toPrettyString();
            return new ResponseEntity<>(str, HttpStatus.OK);

        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @DeleteMapping("details/{id}/projects")
    public ResponseEntity<?> deleteAllProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
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

    @PutMapping("details/{id}/addQuantity")
    public ResponseEntity<?> addQuantity(@PathVariable("id") Long id,
                                         @RequestBody(required = true) Integer quantity)
            throws EntityNotFoundException, IOException, QuantityOfDetailsException, NumberOfQuantityException {
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

    @PutMapping("details/{id}/subtractQuantity")
    public ResponseEntity<?> subtractQuantity(@PathVariable("id") Long id,
                                         @RequestBody(required = true) Integer quantity)
            throws EntityNotFoundException, QuantityOfDetailsException {
        /*
        If everything is OK: API will subtract quantity of details and return JSON(of this updated detail), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: HttpMessageNotReadable(RequestBody isn't represented), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: NumberOfQuantity(Price is not correct), HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(detailServiceImpl.subtractQuantityOfDetails(id, quantity), HttpStatus.OK);
    }

    @GetMapping("/details/{idDetail}/projects/{idProject}")
    public ResponseEntity<?> getProjectFromDetail(@PathVariable(value = "idDetail") Long idDetail,
                                                  @PathVariable("idProject") Long idProject)
            throws EntityNotFoundException, DetailInfoNotFoundException {
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
                                                @PathVariable("idProject") Long idProject)
            throws QuantityOfDetailsException, EntityNotFoundException, IOException {
        /*
        Method where you can add project to detail and fill in quantity of this details in project
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
                                                     @PathVariable("idProject") Long idProject)
            throws EntityNotFoundException, DetailInfoNotFoundException {
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
