package com.sonfl.datahub.controllers;

import com.sonfl.datahub.api.model.ProductDTO;
import com.sonfl.datahub.api.model.ProductListDTO;
import com.sonfl.datahub.orders.OrderingHistory;
import com.sonfl.datahub.orders.ProductOrder;
import com.sonfl.datahub.services.ProductService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;
    private OrderingHistory orderingHistory;
    private ProductOrder productOrder;

    public ProductController(ProductService productService, OrderingHistory orderingHistory, ProductOrder productOrder) {
        this.productService = productService;
        this.orderingHistory = orderingHistory;
        this.productOrder = productOrder;
    }

    @PostMapping("/private/products/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody ProductDTO newProduct) {
        return productService.save(newProduct);
    }

    @DeleteMapping("/private/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping(value = "/products", params = "name")
    public ProductListDTO getProductByMissionName(@RequestParam String name) {
        return hideInfoIfNecessary(new ProductListDTO(productService.getAllProductsByMissionName(name)));

    }

    @GetMapping(value = "/products", params = "type")
    public ProductListDTO getProductsByImageryType(@RequestParam String type) {
        return hideInfoIfNecessary(new ProductListDTO(productService.getAllProductsByMissionImageryType(type)));
    }

    @GetMapping(value = "/products", params = {"startDate", "endDate"})
    public ProductListDTO getProductsBetweenDates(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                  @RequestParam("startDate") Date startDate,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                  @RequestParam("endDate") Date endDate) {
        return hideInfoIfNecessary(new ProductListDTO(productService.getAllProductsByDateBetween(startDate, endDate)));
    }

    @GetMapping(value = "/products", params = "afterDate")
    public ProductListDTO getProductsAfterDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                               @RequestParam Date afterDate) {


        return hideInfoIfNecessary(new ProductListDTO(productService.getAllProductsByDateAfter(afterDate)));
    }

    @GetMapping(value = "/products", params = "beforeDate")
    public ProductListDTO getProductsBeforeDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                @RequestParam Date beforeDate) {
        return hideInfoIfNecessary(new ProductListDTO(productService.getAllProductsByDateBefore(beforeDate)));
    }

    @GetMapping("/products/order")
    @ResponseStatus(HttpStatus.OK)
    public void orderProductsById(@RequestParam("products") Long[] products) {
        if (!isManager()) {
            productOrder.processProducts(products);
        }
    }

    @GetMapping("/products/order/history")
    public ProductListDTO getOrderHistory(@RequestParam("number") Integer number) {
        List<Long> list = orderingHistory.getLast(number);
        if (!isManager() || list.size() == 0) {
            Long[] orders = list.toArray(new Long[list.size()]);
            return new ProductListDTO(productService.getAllProductsById(orders));
        }
        return new ProductListDTO(Collections.emptyList());
    }

    private ProductListDTO hideInfoIfNecessary(ProductListDTO productListDTO) {
        if (!isManager()) {
            hidePathToFile(productListDTO);
        }
        return productListDTO;
    }

    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString().equals("ROLE_MANAGER");
    }

    private void hidePathToFile(ProductListDTO productListDTO) {
        for (ProductDTO p :
                productListDTO.getProducts()) {
            p.setPathToProduct("secret");
        }
    }
}
