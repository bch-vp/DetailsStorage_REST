package org.bch_vp.service;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailInfoNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.DetailNotFoundException;
import org.bch_vp.entity.ExceptionHandler.entityNotFound.ProjectNotFoundException;
import org.bch_vp.entity.Project;

import java.util.List;


public interface DetailService {
    Detail saveDetail(Detail detail);
    boolean deleteDetailById(Long id) throws DetailNotFoundException;
    List<Detail> findAll();
    Detail findDetailById(Long id) throws DetailNotFoundException;
    Detail addAvailableDetails(Long id, Integer quantity) throws DetailNotFoundException;
    public Detail addQuantityOfDetails(Long id, Integer quantity) throws DetailNotFoundException;
    public boolean deleteAllDetails();
    public boolean deleteAllProjectsFromDetail(Long id) throws  DetailNotFoundException;
    public Detail updateDetail(Long id, Detail detail) throws DetailNotFoundException;
    boolean deleteProjectInDetail(Long idDetail, Long idProject) throws DetailInfoNotFoundException, DetailNotFoundException, ProjectNotFoundException;
    Project findProjectInDetail(Long idDetail, Long idProject) throws DetailNotFoundException, ProjectNotFoundException, DetailInfoNotFoundException;
}
