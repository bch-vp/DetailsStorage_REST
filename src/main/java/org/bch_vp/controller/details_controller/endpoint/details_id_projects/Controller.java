package org.bch_vp.controller.details_controller.endpoint.details_id_projects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;
import org.bch_vp.entity.Project;
import org.bch_vp.service.impl.DetailInfoServiceImpl;
import org.bch_vp.service.impl.DetailServiceImpl;
import org.bch_vp.util.JsonUtil;
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
    private DetailInfoServiceImpl detailInfoServiceImpl;

    @GetMapping("/details/{id}/projects")
    public ResponseEntity<?> getAllProjectsFromDetail(@PathVariable("id") Long id) throws EntityNotFoundException {
        /*
        If everything is OK: API will send JSON of array(also it can be empty array), which contains projects from detail with {id}, HttpStatus.OK
        In other cases API will send:
            - JSON about exception: EntityNotFound(detail) with {id}, HttpStatus.NOT_FOUND(404)
            - jSON about exception: converting error {id}, HttpStatus.BAD_REQUEST(400)
            - JSON about exception: unknown error, HttpStatus.INTERNAL_SERVER_ERROR(500)
        */
//        List<DetailInfo> list = detailServiceImpl
//                .findEntityById(id)
//                .getDetailsInfo();
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode objectNode;
//        ArrayNode arrayNode = objectMapper.createArrayNode();
//        arrayNode.addPOJO(list.get(0));
//        ((ObjectNode) arrayNode.get(0)).put("quantityInUsed", "aefaefaefaefaevavaef");
//
//
//        List<Project> projects = detailServiceImpl
//                .findEntityById(id)
//                .getDetailsInfo()
//                .stream()
//                .map(DetailInfo::getProject)
//                .collect(Collectors.toList());
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
                System.out.println(objNode);
            }
            System.out.println(node.asText());
            String str = node.toPrettyString();
            return new ResponseEntity<>(str, HttpStatus.OK);

        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @DeleteMapping("/details/{id}/projects")
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
}
