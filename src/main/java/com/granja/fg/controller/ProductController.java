package com.granja.fg.controller;

import com.granja.fg.exception.ValueDontFindException;
import com.granja.fg.model.Product;
import com.granja.fg.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//http://localhost:8080/fg-app/
@RequestMapping("fg-app")
@CrossOrigin(value = "http://localhost:3000")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    @GetMapping("/insumos")
    public List<Product> getProducts() {
        var products = productService.listProducts();
        products.forEach((product -> logger.info(product.toString())));
        return products;
    }

    @GetMapping("/insumos/{idProduct}")
    public ResponseEntity<Product>
    getProductById(@PathVariable Integer idProduct){
       Product product = productService.findProductById(idProduct);
       if(product == null) {
           throw new ValueDontFindException("No se encontró el producto con id: " + idProduct);
       }
        return ResponseEntity.ok(product);
    }
}
