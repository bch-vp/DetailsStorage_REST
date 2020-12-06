package org.bch_vp.details_storage.entity;

import lombok.Getter;
import lombok.Setter;
import org.bch_vp.details_storage.exception_handler.exception.NumberOfQuantityException;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public abstract class AbstractEntity{

    @Id
    //  @Setter(AccessLevel.NONE)
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    public abstract Long getId();
    public abstract List<DetailInfo> getDetailsInfo();
    public abstract AbstractEntity update(Map<String, Object> mapRequestBody) throws NumberOfQuantityException;
}
