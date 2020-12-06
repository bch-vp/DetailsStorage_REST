package org.bch_vp.details_storage.service;

import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.exception_handler.exception.EntityNotFoundException;
import org.bch_vp.details_storage.exception_handler.exception.QuantityOfDetailsException;


public interface DetailService {
    Detail addAvailableDetails(Long id, Integer quantity) throws EntityNotFoundException;
    Detail addQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException, QuantityOfDetailsException;
    Detail subtractQuantityOfDetails(Long id, Integer quantity) throws EntityNotFoundException, QuantityOfDetailsException;
}
