package org.bch_vp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
    public abstract AbstractEntity update(Map<String, Object> mapRequestBody);
}
