package io.github.besi97.product.repository;

import io.github.besi97.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Override
    List<Product> findAllById(Iterable<String> ids);
}
