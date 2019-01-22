package com.sonfl.datahub.api.mapper.impl;

import com.sonfl.datahub.api.model.ProductDTO;
import com.sonfl.datahub.models.Product;
import com.sonfl.datahub.services.MissionService;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperResolver {

    private MissionService missionService;

    public ProductMapperResolver(MissionService missionService) {
        this.missionService = missionService;
    }

    @ObjectFactory
    public Product resolve(ProductDTO productDTO, @TargetType Class<Product> type) {
        Product product = new Product();
        product.setMission(missionService.getMissionByName(productDTO.getMissionName()));
        return product;
    }
}
