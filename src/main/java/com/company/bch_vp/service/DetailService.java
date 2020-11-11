package com.company.bch_vp.service;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.ExceptionHandler.EntityNotFoundException;

import java.util.List;


public interface DetailService {
    Detail saveDetail(Detail detail);
    boolean deleteDetailById(Long id);
    List<Detail> findAll();
    Detail findDetailById(Long id);
    void addAvailableDetails(Long id, Integer quantity);
    public void addQuantityOfDetails(Long id, Integer quantity);
    public boolean deleteAllDetails();
    public boolean deleteAllProjectsFromDetail(Long id) throws EntityNotFoundException;
    public boolean updateDetail(Long id, Detail detail);
    void deleteProjectInDetail(Long idDetail, Long idProject);
}
