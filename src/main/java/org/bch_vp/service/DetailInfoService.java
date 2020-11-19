package org.bch_vp.service;

import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.ExceptionHandler.entity.EntityNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entity.QuantityOfDetailsException;

import java.util.List;

public interface DetailInfoService {
    boolean joinDetailAndProject(Integer countOfDetail, Long idDetail, Long idProject) throws QuantityOfDetailsException, EntityNotFoundException;
    List<DetailInfo> findAll();
    void addQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject) throws EntityNotFoundException;
    DetailInfo findById(Long idDetail, Long idProject) throws EntityNotFoundException;
    void subtractQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject) throws EntityNotFoundException;
}
