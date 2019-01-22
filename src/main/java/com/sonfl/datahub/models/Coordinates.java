package com.sonfl.datahub.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.awt.geom.Point2D;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Coordinates extends BaseEntity {
    private Point2D.Double topLeft;
    private Point2D.Double topRight;
    private Point2D.Double bottomLeft;
    private Point2D.Double bottomRight;


    public Coordinates(Point2D.Double topLeft, Point2D.Double topRight, Point2D.Double bottomLeft, Point2D.Double bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}
