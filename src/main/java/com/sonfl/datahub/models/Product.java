package com.sonfl.datahub.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"mission", "coordinates"})
@Entity
public class Product extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_mission")
    private Mission mission;
    private Date date;
    @OneToOne
    private Coordinates coordinates;
    private BigDecimal price;
    private String pathToProduct;
}
