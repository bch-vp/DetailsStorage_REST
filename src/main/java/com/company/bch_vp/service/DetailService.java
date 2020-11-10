package com.company.bch_vp.service;

import com.company.bch_vp.entity.Detail;

import java.util.List;


public interface DetailService {
    Detail saveDetail(Detail detail);
    void deleteDetailById(Long id);
    List<Detail> findAll();
    Detail findDetailById(Long id);
    void addAvailableDetails(Long id, Integer quantity);
    public void addQuantityOfDetails(Long id, Integer quantity);

}
