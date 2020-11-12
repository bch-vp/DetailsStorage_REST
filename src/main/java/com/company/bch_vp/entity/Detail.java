package com.company.bch_vp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Detail {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JsonManagedReference(value="user-movement")
   // @JsonIgnore
    private List<DetailInfo> detailsInfo = new ArrayList<>();

    //delete!
    public Detail(String detailName) {
        this.detailName = detailName;
    }

    public Detail(String detailNamem, Integer quantityOfAll){
        this.detailName=detailNamem;
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

    public void addDetailInfo(DetailInfo detailInfo){
        detailsInfo.add(detailInfo);
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

    public void subtractAvailableDetails(Integer quantityOfAvailable){
        this.quantityOfAvailable-=quantityOfAvailable;
    }

    public Detail updateDetail(Detail detail){
        if(!detail.detailName.isEmpty()){
            this.detailName=detail.getDetailName();
        }
        if(!detail.type.isEmpty()){
            this.type=detail.getType();
        }
        if (!detail.production.isEmpty()) {
            this.production=detail.getProduction();
        }
        if(detail.price!=null){
            this.price=detail.getPrice();
        }
        if(!detail.storage.isEmpty()){
            this.storage=detail.getStorage();
        }
        return this;
    }
}
