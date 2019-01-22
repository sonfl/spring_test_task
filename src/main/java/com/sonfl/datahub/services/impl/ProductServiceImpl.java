package com.sonfl.datahub.services.impl;

import com.sonfl.datahub.api.mapper.ProductMapper;
import com.sonfl.datahub.api.model.ProductDTO;
import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Product;
import com.sonfl.datahub.repositories.ProductRepository;
import com.sonfl.datahub.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAllProductsByMissionName(String name) {
        return productRepository.findAll()
                .stream()
                .filter(e -> e.getMission().getName().equals(name))
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductDTO> getAllProductsByMissionImageryType(String type) {
        return productRepository.findAll()
                .stream()
                .filter(e -> e.getMission().getImageryType().equals(ImageryType.valueOf(type)))
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByDateAfter(Date date) {
        return productRepository.findAll()
                .stream()
                .filter(e -> e.getDate().after(date))
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByDateBefore(Date date) {
        return productRepository.findAll()
                .stream()
                .filter(e -> e.getDate().before(date))
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByDateBetween(Date first, Date second) {
        return productRepository.findAll()
                .stream()
                .filter(e -> e.getDate().after(first))
                .filter(e -> e.getDate().before(second))
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        return productMapper.productToProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getAllProductsById(Long[] ids) {
        List<ProductDTO> products = new ArrayList<>();
        for (long l :
                ids) {
            products.add(productMapper.productToProductDTO(productRepository.findById(l).orElse(null)));
        }
        return products;
    }
}
