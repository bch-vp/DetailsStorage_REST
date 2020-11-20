package org.bch_vp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Detail extends AbstractEntity {



    @NotBlank(message="Detail Name is required")
    private String detailName;
    private String type;
    private String production;
    @NotNull(message="Quantity is required")
    @Min(value = 1,message = "Filled not correct")
    private Integer quantityOfAll;
    private Integer quantityOfAvailable;
    @NotNull(message="Price is required")
    @Min(value = 0,message = "Filled not correct")
    private Double price;
    @NotBlank(message="Storage is required")
    private String storage;

    @OneToMany(mappedBy = "detail", fetch = FetchType.EAGER)
    //@JsonManagedReference(value="user-movement")
    @JsonIgnore
    private List<DetailInfo> detailsInfo = new ArrayList<>();

    public void addDetailInfo(DetailInfo detailInfo){
        detailsInfo.add(detailInfo);
    }

    //delete!
    public Detail(String detailName) {
        this.detailName = detailName;
    }

    public Detail(String detailName, Integer quantityOfAll){
        this.detailName=detailName;
        this.quantityOfAll=quantityOfAll;
        this.quantityOfAvailable=quantityOfAll;
    }

    public Detail(String detailName, String type, String production, Integer quantityOfAll, Double price, String storage) {
        this.detailName = detailName;
        this.type = type;
        this.production = production;
        this.quantityOfAll = quantityOfAll;
        //construct new detail,that's why  quantityOfAvailable == quantityOfAll
        this.quantityOfAvailable=quantityOfAll;
        this.price = price;
        this.storage = storage;
    }

    public Detail addAvailableDetails(Integer quantityOfAvailable){
        this.quantityOfAvailable+=quantityOfAvailable;
        return this;
    }

    public Detail addQuantityOfDetails(Integer quantityOfAvailable){
        this.quantityOfAvailable+=quantityOfAvailable;
        this.quantityOfAll+=quantityOfAvailable;
        return this;
    }

    public void subtractAvailableDetails(Integer quantityOfAvailable) {
        this.quantityOfAvailable -= quantityOfAvailable;
    }

    @Override
    public AbstractEntity update(Map<String, Object> mapRequestBody) {
        String detailName = (String) mapRequestBody.get("detailName");
        if (!detailName.isEmpty()) {
            this.detailName = detailName;
        }
        String type = (String) mapRequestBody.get("type");
        if (!type.isEmpty()) {
            this.type = type;
        }
        String production = (String) mapRequestBody.get("production");
        if (!production.isEmpty()) {
            this.production = production;
        }
        Double price = Double.valueOf((String) mapRequestBody.get("price"));
        if (price != null) {
            this.price = price;
            //write recalculate price
        }
        String storage = (String) mapRequestBody.get("storage");
        if (!storage.isEmpty()) {
            this.storage = storage;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Detail)) return false;
        Detail detailInfo = (Detail) o;
        return Objects.equals(getDetailName(), detailInfo.getDetailName()) &&
                Objects.equals(getType(), detailInfo.getType()) &&
                Objects.equals(getQuantityOfAll(), detailInfo.getQuantityOfAll()) &&
                Objects.equals(getQuantityOfAvailable(), detailInfo.getQuantityOfAvailable()) &&
                Objects.equals(getPrice(), detailInfo.getPrice()) &&
                Objects.equals(getStorage(), detailInfo.getStorage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" +
                ", detailName='" + detailName + '\'' +
                ", type='" + type + '\'' +
                ", production='" + production + '\'' +
                ", quantityOfAll=" + quantityOfAll +
                ", quantityOfAvailable=" + quantityOfAvailable +
                ", price=" + price +
                ", storage='" + storage + '\'' +
                '}';
    }
}
