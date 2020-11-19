package org.bch_vp.repository;

import org.bch_vp.entity.DetailInfo;
import org.bch_vp.entity.IdDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailInfoRepository extends JpaRepository<DetailInfo, IdDetailInfo> {
    Optional<DetailInfo> findById(IdDetailInfo idDetailInfo);
}


