package com.granja.fg.repository;

import com.granja.fg.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Integer> {

}
