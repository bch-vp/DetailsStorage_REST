package org.bch_vp.service;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.exception_handler.entity.EntityNotFoundException;
import org.bch_vp.entity.exception_handler.entity.QuantityOfDetailsException;


public interface DetailService {
    Detail addAvailableDetails(Long id, Integer quantity) throws EntityNotFoundException;
    Detail addQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException, QuantityOfDetailsException;
    Detail subtractQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException, QuantityOfDetailsException;
}
