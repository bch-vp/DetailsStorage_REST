package org.bch_vp.details_storage.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class IdDetailInfo implements Serializable {

    @Column(name = "detail_id")
    protected Long detailId;
    @Column(name = "project_id")
    protected Long projectId;

    public IdDetailInfo(Long detailyId, Long projectId) {
        this.detailId = detailyId;
        this.projectId = projectId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public boolean equals(Object o) {
        if (o != null && o instanceof IdDetailInfo) {
            IdDetailInfo that = (IdDetailInfo) o;
            return this.detailId.equals(that.detailId)
                    && this.projectId.equals(that.projectId);
        }
        return false;
    }

    public int hashCode() {
        return detailId.hashCode() + projectId.hashCode();
    }

}
