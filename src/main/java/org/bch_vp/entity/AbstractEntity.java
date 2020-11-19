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

@MappedSuperclass
public abstract class AbstractEntity{
    public abstract Long getId();
    public abstract List<DetailInfo> getDetailsInfo();
    public abstract AbstractEntity update(AbstractEntity objectDetail);
}
