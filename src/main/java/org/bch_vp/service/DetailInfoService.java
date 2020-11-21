package org.bch_vp.service;

import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;

import java.io.IOException;
import java.util.List;

public interface DetailInfoService {
    boolean joinDetailAndProject(Integer countOfDetail, Long idDetail, Long idProject) throws QuantityOfDetailsException, EntityNotFoundException;
    List<DetailInfo> findAll();
    boolean addQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException;
    DetailInfo findById(Long idDetail, Long idProject) throws EntityNotFoundException;
    void subtractQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException;
}
