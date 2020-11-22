package org.bch_vp.service.impl;

import org.bch_vp.entity.Detail;
import org.bch_vp.entity.Project;
import org.bch_vp.repository.DetailRepository;
import org.bch_vp.repository.ProjectRepository;
import org.bch_vp.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractStorageServiceImpl<Project, Detail, ProjectRepository, DetailRepository> implements ProjectService {
    private ProjectServiceImpl() {
        super(Project.class, Detail.class);
    }
}
