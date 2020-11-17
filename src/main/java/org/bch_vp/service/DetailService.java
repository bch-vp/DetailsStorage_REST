package org.bch_vp.service;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import org.bch_vp.entity.Project;


public interface DetailService {
    Detail addAvailableDetails(Long id, Integer quantity) throws EntityNotFoundException;
    public Detail addQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException;
}
