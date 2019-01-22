package com.sonfl.datahub.services;

import com.sonfl.datahub.api.model.ProductDTO;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProductsByMissionName(String name);

    List<ProductDTO> getAllProductsByMissionImageryType(String type);

    List<ProductDTO> getAllProductsByDateAfter(Date date);

    List<ProductDTO> getAllProductsByDateBefore(Date date);

    List<ProductDTO> getAllProductsByDateBetween(Date first, Date second);

    List<ProductDTO> getAllProductsById(Long[] ids);

    ProductDTO save(ProductDTO productDTO);

    void deleteById(Long id);
}
