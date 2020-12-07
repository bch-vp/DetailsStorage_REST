package org.bch_vp.details_storage.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.entity.DetailInfo;
import org.bch_vp.details_storage.entity.Project;
import org.bch_vp.details_storage.exception_handler.exception.*;
import org.bch_vp.details_storage.service.impl.DetailInfoServiceImpl;
import org.bch_vp.details_storage.service.impl.DetailServiceImpl;
import org.bch_vp.details_storage.service.impl.ProjectServiceImpl;
import org.bch_vp.details_storage.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProjectsController {

    @Autowired
    private DetailServiceImpl detailServiceImpl;
    @Autowired
    private ProjectServiceImpl projectServiceImpl;
    @Autowired
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("projects")
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

    @PostMapping("projects")
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

    @DeleteMapping("projects")
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

    @GetMapping("projects/{id}")
    public ResponseEntity<?> getProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will return JSON(of this project), HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>((Project)projectServiceImpl.findEntityById(id), HttpStatus.OK);

    }

    @PutMapping("projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("id") Long id,
                                           @RequestBody (required = true) String jsonRequestBody)
            throws EntityNotFoundException, QuantityOfDetailsException, IOException, NumberOfQuantityException {
        /*
        If everything is OK: API will update project and return JSON(of this updated project), HttpStatus.OK
        In other cases API will send:
            - HttpStatus.INTERNAL_SERVER_ERROR(500)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: @Valid project, BAD_REQUEST(400)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return new ResponseEntity<>(projectServiceImpl.updateEntity(id, jsonRequestBody), HttpStatus.OK);
    }

    @DeleteMapping("projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will delete project and return HttpStatus.OK
        In other cases API will send:
            - HttpStatus.NOT_MODIFIED(304)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return projectServiceImpl.deleteEntityById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("projects/{id}/details")
    public ResponseEntity<?> getAllDetailsFromProject(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send JSON of array(also it can be empty array), which contains details from project with {id}, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        List<DetailInfo> detailsInfo = projectServiceImpl
                .findEntityById(id)
                .getDetailsInfo();
        List<Detail> details = detailsInfo
                .stream()
                .map(DetailInfo::getDetail)
                .collect(Collectors.toList());

        if (!details.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.valueToTree(details);

            int i = 0;
            for (JsonNode objNode : node) {
                ((ObjectNode) objNode).put("quantityInUsed", detailsInfo.get(i).getQuantityDetailsUsed());
                i++;
            }
            String str = node.toPrettyString();
            return new ResponseEntity<>(str, HttpStatus.OK);
        }
        return new ResponseEntity<>(details, HttpStatus.OK);
    }


    @DeleteMapping("projects/{id}/details")
    public ResponseEntity<?> deleteAllDetailsFromProject(@PathVariable("id") Long id) throws EntityNotFoundException {
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

    @GetMapping("projects/{idProject}/details/{idDetail}")
    public ResponseEntity<?> getDetailFromProject(@PathVariable(value = "idDetail") Long idDetail,
                                                  @PathVariable("idProject") Long idProject)
            throws EntityNotFoundException, DetailInfoNotFoundException, DetailInfoNotFoundException {
        /*
        If everything is OK: API will send JSON of detail from project and HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        Detail detail = (Detail) projectServiceImpl.findInnerEntityFromEntity(idProject,idDetail);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @PostMapping("projects/{idProject}/details/{idDetail}")
    public ResponseEntity<?> addDetailToProject(@RequestBody String jsonQuantityOfDetails,
                                                @PathVariable(value = "idDetail") Long idDetail,
                                                @PathVariable("idProject") Long idProject)
            throws QuantityOfDetailsException, EntityNotFoundException, IOException {
        /*
        Method where you can add detail to project with quantity of details(quantity > 0 && quantity < quantityOfAvailable)
        If everything is OK: API will send HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {id}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: AlreadyHasRelations, HttpStatus.NOT_FOUND(400). It means, that detail alredy contains this project
            - jSON about exception: QuantityOfDetails, HttpStatus.NOT_FOUND(400)
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

    @DeleteMapping("projects/{idProject}/details/{idDetail}")
    public ResponseEntity<?> deleteDetailFromProject(@PathVariable(value = "idDetail") Long idDetail,
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
        return projectServiceImpl.deleteInnerEntityFromEntity(idProject, idDetail)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("projects/{idProject}/details/{idDetail}/addQuantity")
    public ResponseEntity<?> addQuantityOfDetails(@PathVariable("idDetail") Long idDetail,
                                                  @PathVariable("idProject") Long idProject,
                                                  @RequestBody(required = true) String jsonQuantityRequestBody) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException {
        /*
        If everything is OK:
            - API will updated quantity of details in project, HttpStatus.OK
        In other cases API will send:
            In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailInfoServiceImpl.addQuantityOfDetailsInProject(jsonQuantityRequestBody, idDetail, idProject)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("projects/{idProject}/details/{idDetail}/subtractQuantity")
    public ResponseEntity<?> subtractQuantityOfDetails(@PathVariable("idDetail") Long idDetail,
                                                  @PathVariable("idProject") Long idProject,
                                                  @RequestBody(required = true) String jsonQuantityRequestBody) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException {
        /*
        If everything is OK:
            - API will updated quantity of details in project, HttpStatus.OK
        In other cases API will send:
            In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {idDetail}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(project) with {idProject}, HttpStatus.NOT_FOUND(404)
            - JSON about exception: EntityNotFound(detailInfo),which means project and detail didn't joined, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {idDetail}, HttpStatus.BAD_REQUEST(400)
            - jSON about exception: converting error {idProject}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
        return detailInfoServiceImpl.subtractQuantityOfDetailsInProject(jsonQuantityRequestBody, idDetail, idProject)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
