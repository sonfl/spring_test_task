package com.sonfl.datahub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"products"})
@Entity
public class Mission extends BaseEntity {
    private String name;
    @Enumerated(value = EnumType.STRING)
    private ImageryType imageryType;
    private Date startDate;
    private Date finishDate;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mission")
    private Set<Product> products = new HashSet<>();
}
