package com.granja.fg.controller;

import com.granja.fg.exception.ValueDontFindException;
import com.granja.fg.model.Product;
import com.granja.fg.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
//http://localhost:8080/fg-app/
@RequestMapping("fg-app")
@CrossOrigin(value = "http://localhost:5173")
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

    @PostMapping("/insumos")
    public Product addProduct(@RequestBody Product product) {
        logger.info("Producto a agregar" + product);
        return productService.saveProduct(product);
    }

    @GetMapping("/insumos/{idProduct}")
    public ResponseEntity<Product>
    getProductById(@PathVariable Integer idProduct){
       Product product = productService.findProductById(idProduct);
       if(product == null) throw new ValueDontFindException("No se encontró el producto con id: " + idProduct);
        ResponseEntity<Product> ok = ResponseEntity.ok(product);
        return ok;
    }


    @PutMapping("/insumos/{idProduct}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer idProduct, @RequestBody Product productReceived){
        Product product = productService.findProductById(idProduct);
        if(product == null)
            throw new ValueDontFindException("El id recibido no existe: " + idProduct);
        product.setInDate(productReceived.getInDate());
        product.setName(productReceived.getName());
        product.setType(productReceived.getType());
        product.setPresentation(productReceived.getPresentation());
        product.setQuantity(productReceived.getQuantity());
        product.setPrice(productReceived.getPrice());

        productService.saveProduct(product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/insumos/{idProduct}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Integer idProduct){
        Product product = productService.findProductById(idProduct);
        if(product == null)
            throw new ValueDontFindException("El id recibido no existe: " + idProduct);
        productService.deleteProduct(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
