package org.bch_vp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bch_vp.entity.ExceptionHandler.entity.NumberOfQuantityException;

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
    public AbstractEntity update(Map<String, Object> mapRequestBody) throws NumberOfQuantityException {
        String projectName = (String) mapRequestBody.get("projectName");
        if (projectName != null && !projectName.isEmpty()) {
            this.projectName = projectName;
        }
        String type = (String) mapRequestBody.get("type");
        if (type != null &&!type.isEmpty()) {
            this.type = type;
        }
        String quantity = String.valueOf(mapRequestBody.get("quantity"));
        if(quantity != null && !quantity.isEmpty()) {
            if (quantity.matches("^[0-9]+$") && Integer.parseInt(quantity)>0) {
                this.quantity = Integer.valueOf(quantity);
                //write recalculate price
            } else {
                throw new NumberOfQuantityException(quantity);
            }
        }
        String storage = (String) mapRequestBody.get("storage");
        if (storage != null &&!storage.isEmpty()) {
            this.storage = storage;
        }
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("projectName='").append(projectName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", storage='").append(storage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
