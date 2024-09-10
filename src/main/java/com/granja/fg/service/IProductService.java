package com.granja.fg.service;

import com.granja.fg.model.Product;

import java.util.List;

public interface IProductService {
    public List<Product> listProducts();

    public Product findProductById(Integer idProducto);

    public Product saveProduct(Product product);

    public void deleteProduct(Product product);
}
