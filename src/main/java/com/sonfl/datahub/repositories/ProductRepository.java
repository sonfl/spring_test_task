package com.sonfl.datahub.repositories;

import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByMissionName(String name);

    Product findByMissionImageryType(ImageryType type);

    Product findByDateAfter(Date date);

    Product findByDateBefore(Date date);

    Product findByDateBetween(Date first, Date second);
}
