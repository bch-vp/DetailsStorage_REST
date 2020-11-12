package com.company.bch_vp.service;

import com.company.bch_vp.entity.ExceptionHandler.entityNotFound.ProjectNotFoundException;
import com.company.bch_vp.entity.Project;

import java.util.List;

public interface ProjectService {
    Project saveProject(Project project);
    void deleteProjectById(Long id);
    List<Project> findAll();
    Project findById(Long id) throws ProjectNotFoundException;
    void deleteDetailInProject(Long idDetail, Long idProject);
}
