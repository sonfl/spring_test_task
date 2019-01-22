package com.sonfl.datahub.controllers;

import com.google.gson.reflect.TypeToken;
import com.sonfl.datahub.TestUtils;
import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.api.model.ProductDTO;
import com.sonfl.datahub.api.model.ProductListDTO;
import com.sonfl.datahub.models.Coordinates;
import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Mission;
import com.sonfl.datahub.models.Product;
import com.sonfl.datahub.orders.OrderingHistory;
import com.sonfl.datahub.orders.ProductOrder;
import com.sonfl.datahub.services.MissionService;
import com.sonfl.datahub.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@WithMockUser(username = "manager", roles = "MANAGER")
class ProductControllerTest {

    private static final String MISSION_NAME = "first";
    private static final ImageryType IMAGERY_TYPE = ImageryType.MULTISPECTRAL;
    private static Date DATE = null;
    private static final BigDecimal PRICE = new BigDecimal(100);
    private static final String PATH_TO_FILE = "somepath";
    private ProductDTO productDTO;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    MissionService missionService;

    @MockBean
    OrderingHistory orderingHistory;

    @MockBean
    ProductOrder productOrder;

    @BeforeEach
    void setUp() throws Exception{
        DATE = new SimpleDateFormat("yyyy-MM-dd").parse("1988-06-13");
        productDTO = new ProductDTO();
        productDTO.setPrice(PRICE);
        productDTO.setMissionName(MISSION_NAME);
        productDTO.setImageryType(IMAGERY_TYPE.toString());
        productDTO.setDate(DATE);
        productDTO.setPathToProduct(PATH_TO_FILE);
    }

    @Test
    void addProduct() throws Exception{

        when(productService.save(any(ProductDTO.class))).thenReturn(productDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/products/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.objectToJson(productDTO))).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        verify(productService).save(any(ProductDTO.class));

        ProductDTO resultProductDTO = TestUtils.jsonToObject(result.getResponse().getContentAsString(), ProductDTO.class);
        assertNotNull(resultProductDTO);
        assertEquals(productDTO.getMissionName(), resultProductDTO.getMissionName());
    }

    @Test
    void deleteProduct() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/private/products/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    void getProductByMissionName() throws Exception{

        List<ProductDTO> products = new ArrayList<>();
        products.add(productDTO);



        when(productService.getAllProductsByMissionName(any(String.class))).thenReturn(products);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .param("name","first")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        ProductListDTO returnedList = TestUtils.jsonToList(mvcResult.getResponse().getContentAsString(), new TypeToken<ProductListDTO>(){});
        assertNotNull(returnedList);
        assertEquals(products.size(), returnedList.getProducts().size());


    }

    @Test
    void getProductsByImageryType() throws Exception{

        List<ProductDTO> products = new ArrayList<>();
        products.add(productDTO);

        when(productService.getAllProductsByMissionImageryType(any(String.class))).thenReturn(products);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .param("type","MULTISPECTRAL")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        ProductListDTO returnedList = TestUtils.jsonToList(mvcResult.getResponse().getContentAsString(), new TypeToken<ProductListDTO>(){});

        assertNotNull(returnedList);
        assertEquals(products.size(), returnedList.getProducts().size());
    }

    @Test
    void getProductsBetweenDates() throws Exception{

        List<ProductDTO> products = new ArrayList<>();
        products.add(productDTO);

        when(productService.getAllProductsByDateBetween(any(Date.class), any(Date.class))).thenReturn(products);

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("startDate", new ArrayList<>(Arrays.asList("1960-11-11")));
        map.put("endDate", new ArrayList<>(Arrays.asList("2010-11-11")));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .params(map)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        ProductListDTO returnedList = TestUtils.jsonToList(mvcResult.getResponse().getContentAsString(), new TypeToken<ProductListDTO>(){});

        assertNotNull(returnedList);
        assertEquals(products.size(), returnedList.getProducts().size());
    }

    @Test
    void getProductsAfterDate() throws Exception{
        List<ProductDTO> products = new ArrayList<>();
        products.add(productDTO);

        when(productService.getAllProductsByDateAfter(any(Date.class))).thenReturn(products);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .param("afterDate","1960-11-11")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        ProductListDTO returnedList = TestUtils.jsonToList(mvcResult.getResponse().getContentAsString(), new TypeToken<ProductListDTO>(){});

        assertNotNull(returnedList);
        assertEquals(products.size(), returnedList.getProducts().size());
    }

    @Test
    void getProductsBeforeDate() throws Exception{

        List<ProductDTO> products = new ArrayList<>();
        products.add(productDTO);

        when(productService.getAllProductsByDateBefore(any(Date.class))).thenReturn(products);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .param("beforeDate","2010-05-05")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        ProductListDTO returnedList = TestUtils.jsonToList(mvcResult.getResponse().getContentAsString(), new TypeToken<ProductListDTO>(){});

        assertNotNull(returnedList);
        assertEquals(products.size(), returnedList.getProducts().size());
    }
}