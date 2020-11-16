package org.bch_vp.service;

import org.bch_vp.entity.DetailInfo;

import java.util.List;

public interface DetailInfoService {
    boolean joinDetailAndProject(Integer countOfDetail, Long idDetail, Long idProject);
    List<DetailInfo> findAll();
    void addQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject);
    DetailInfo findById(Long idDetail, Long idProject);
    void subtractQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject);
}
