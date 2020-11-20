package org.bch_vp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project extends AbstractEntity {



    @NotBlank(message = "Project name is required")
    private String projectName;
    private String type;
    @NotNull(message = "Quantity is required")
    @Min(value = 1,message = "Filled not correct")
    private Integer quantity;
    @NotBlank(message = "Storage is required")
    private String storage;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    //@JsonManagedReference(value="user-movement")
    @JsonIgnore
    private List<DetailInfo> detailsInfo = new ArrayList<>();

    public void addDetailInfo(DetailInfo detailInfo){
        detailsInfo.add(detailInfo);
    }

//    int quantityOfDetails= detailMap.getDetails()
//            .stream()
//            .mapToInt(DetailForm::getQuantity)
//            .sum();

    public Double calculatePriceOfProject(){
        if(!getDetailsInfo().isEmpty()){
           return getDetailsInfo()
                   .stream()
                   .mapToDouble(detailInfo -> detailInfo.getDetail().getPrice() * detailInfo.getQuantityDetailsUsed())
                   .sum();
        }
        return (double)0;
    }

    //delete!
    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project(String projectName, String type, Integer quantity, String storage) {
        this.projectName = projectName;
        this.type = type;
        this.quantity = quantity;
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectName, project.projectName) &&
                Objects.equals(type, project.type) &&
                Objects.equals(quantity, project.quantity) &&
                Objects.equals(storage, project.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override// rewrite
    public AbstractEntity update(Map<String, Object> mapRequestBody) {
        return null;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" +
                ", projectName='" + projectName + '\'' +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", storage='" + storage + '\'' +
                '}';
    }
}
