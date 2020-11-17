package org.bch_vp.repository;

import org.bch_vp.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface StorageRepository<Entity extends AbstractEntity> extends JpaRepository<Entity, Long> {
}
