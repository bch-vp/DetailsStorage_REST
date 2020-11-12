package com.company.bch_vp.service;

import com.company.bch_vp.entity.Detail;
import com.company.bch_vp.entity.DetailInfo;
import com.company.bch_vp.entity.IdDetailInfo;
import com.company.bch_vp.entity.Project;
import com.company.bch_vp.repository.DetailRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetailInfoService {
    boolean joinDetailAndProject(Integer countOfDetail, Long idDetail, Long idProject);
    List<DetailInfo> findAll();
    void addQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject);
    DetailInfo findById(Long idDetail, Long idProject);
    void subtractQuantityOfDetailsInProject(Integer quantity, Long idDetail, Long idProject);
}
