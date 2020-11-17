package org.bch_vp.repository;

import org.bch_vp.entity.Detail;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface DetailRepository extends StorageRepository<Detail>{
}
