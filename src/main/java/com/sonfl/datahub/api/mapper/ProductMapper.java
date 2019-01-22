package com.sonfl.datahub.api.mapper;

import com.sonfl.datahub.api.mapper.impl.ProductMapperResolver;
import com.sonfl.datahub.api.model.ProductDTO;
import com.sonfl.datahub.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapperResolver.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "mission.name", target = "missionName")
    @Mapping(source = "mission.imageryType", target = "imageryType")
    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);

}
