package org.bch_vp.details_storage.service;

import org.bch_vp.details_storage.entity.DetailInfo;
import org.bch_vp.details_storage.exception_handler.exception.DetailInfoNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.EntityNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.QuantityOfDetailsException;

import java.io.IOException;
import java.util.List;

public interface DetailInfoService {
    boolean joinDetailAndProject(Integer quantity, Long idDetail, Long idProject) throws QuantityOfDetailsException, EntityNotFoundException;
    List<DetailInfo> findAll();
    boolean addQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException;
    boolean subtractQuantityOfDetailsInProject(String jsonQuantityRequestBody, Long idDetail, Long idProject) throws EntityNotFoundException, IOException, QuantityOfDetailsException, DetailInfoNotFoundException;
    DetailInfo findById(Long idDetail, Long idProject) throws EntityNotFoundException;
}
