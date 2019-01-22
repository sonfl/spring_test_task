package com.sonfl.datahub.api.model;

import com.sonfl.datahub.models.Coordinates;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDTO {

    //private Mission mission;

    private String missionName;
    private String imageryType;
    private Date date;
    private Coordinates coordinates;
    private BigDecimal price;
    private String pathToProduct;
}
