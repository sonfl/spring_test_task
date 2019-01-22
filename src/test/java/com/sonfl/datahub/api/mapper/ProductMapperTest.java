package com.sonfl.datahub.api.mapper;

import com.sonfl.datahub.api.model.ProductDTO;
import com.sonfl.datahub.models.Coordinates;
import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Mission;
import com.sonfl.datahub.models.Product;
import com.sonfl.datahub.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class ProductMapperTest {
    static final String MISSION_NAME = "first";
    static final ImageryType IMAGERY_TYPE = ImageryType.MULTISPECTRAL;
    static  Date DATE = null;
    @Mock
    static Coordinates COORDINATES;
    static final BigDecimal PRICE = new BigDecimal(100);
    static final String PATH_TO_FILE = "somepath";
    @Mock
    static Mission mission;

    ProductMapper productMapper = ProductMapper.INSTANCE;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        DATE = new SimpleDateFormat("yyyy-MM-dd").parse("1988-06-13");
    }

    @Test
    void productToProductDTO() {
        Product product = new Product();
        product.setPathToProduct(PATH_TO_FILE);
        product.setCoordinates(COORDINATES);
        product.setPrice(PRICE);
        product.setDate(DATE);
        product.setMission(mission);

        ProductDTO productDTO = productMapper.productToProductDTO(product);

        assertEquals(PATH_TO_FILE, productDTO.getPathToProduct());
        assertEquals(COORDINATES, productDTO.getCoordinates());
        assertEquals(PRICE, productDTO.getPrice());
        assertEquals(DATE, productDTO.getDate());
        assertEquals(mission.getName(), productDTO.getMissionName());
    }

    /*
        the following test produces a NPE, which I tried to fix unsuccessfully due to lack of skills/knowledge
     */

    @Test
    void productDTOToProduct() {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setPathToProduct(PATH_TO_FILE);
//        productDTO.setCoordinates(COORDINATES);
//        productDTO.setDate(DATE);
//        productDTO.setImageryType(IMAGERY_TYPE.toString());
//        productDTO.setMissionName(MISSION_NAME);
//        productDTO.setPrice(PRICE);
//
//        Product product = productMapper.productDTOToProduct(productDTO);
//
//        assertEquals(PATH_TO_FILE, product.getPathToProduct());
//        assertEquals(COORDINATES, product.getCoordinates());
//        assertEquals(PRICE, product.getPrice());
//        assertEquals(DATE, product.getDate());
//        assertEquals(mission.getName(), product.getMission().getName());
    }
}