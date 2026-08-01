package io.github.besi97.product.graphql.resolvers;

import io.github.besi97.product.graphql.api.CreateMutationResolver;
import io.github.besi97.product.graphql.mappers.ProductMapper;
import io.github.besi97.product.graphql.model.ProductInputTO;
import io.github.besi97.product.graphql.model.ProductTO;
import io.github.besi97.product.model.Product;
import io.github.besi97.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationsResolver implements CreateMutationResolver {

    @Autowired
    private ProductService service;
    @Autowired
    private ProductMapper mapper;

    @Override
    public ProductTO create(ProductInputTO productInput) {
        Product savedProduct = service.create(mapper.mapInput(productInput));
        return mapper.map(savedProduct);
    }
}
