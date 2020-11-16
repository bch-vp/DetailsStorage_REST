package org.bch_vp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class DetailInfo {
    @EmbeddedId
    @Getter @Setter(AccessLevel.NONE)
    private IdDetailInfo id = new IdDetailInfo();

    @Getter @Setter
    private Integer quantityDetailsUsed;

    @Getter @Setter
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="detail_id",insertable = false, updatable = false)
  //  @JsonBackReference(value="user-movement")
    private Detail detail;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", insertable = false, updatable = false)
  //  @JsonBackReference
    private Project project;

    public DetailInfo(Integer quantityDetailsUsed, Detail detail, Project project) {
        this.quantityDetailsUsed = quantityDetailsUsed;
        this.detail = detail;
        this.project = project;

        this.id.detailId=detail.getId();
        this.id.projectId=project.getId();

        project.getDetailsInfo().add(this);
        detail.getDetailsInfo().add(this);
    }

    public DetailInfo addQuantityofDetailsUsed(Integer quantity){
        this.quantityDetailsUsed+=quantity;
        return this;
    }

    public DetailInfo subtractQuantityofDetailsUsed(Integer quantity){
        this.quantityDetailsUsed-=quantity;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof DetailInfo)) return false;
        DetailInfo detailInfo = (DetailInfo) o;
        return Objects.equals(getDetail(), detailInfo.getDetail()) &&
                Objects.equals(getProject(), detailInfo.getProject()) &&
                Objects.equals(getId().getDetailId(), detailInfo.getId().getDetailId()) &&
                Objects.equals(getId().getProjectId(), detailInfo.getId().getProjectId()) &&
                Objects.equals(getQuantityDetailsUsed(), detailInfo.getQuantityDetailsUsed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
