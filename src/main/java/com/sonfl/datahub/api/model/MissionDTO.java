package com.sonfl.datahub.api.model;

import com.sonfl.datahub.models.ImageryType;
import lombok.Data;

import java.util.Date;

@Data
public class MissionDTO {
    private String name;
    private ImageryType imageryType;
    private Date startDate;
    private Date finishDate;
}
