package org.bch_vp.details_storage.service.impl;

import org.bch_vp.details_storage.entity.Detail;
import org.bch_vp.details_storage.entity.Project;
import org.bch_vp.details_storage.repository.DetailRepository;
import org.bch_vp.details_storage.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractStorageServiceImpl<Project, Detail, ProjectRepository, DetailRepository> {
    private ProjectServiceImpl() {
        super(Project.class, Detail.class);
    }
}
