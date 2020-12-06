package org.bch_vp.details_storage.repository;

import org.bch_vp.details_storage.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface StorageRepository<Entity extends AbstractEntity> extends JpaRepository<Entity, Long> {
}
