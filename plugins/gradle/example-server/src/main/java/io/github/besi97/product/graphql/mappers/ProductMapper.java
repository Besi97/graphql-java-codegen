package io.github.besi97.product.graphql.mappers;

import io.github.besi97.product.graphql.model.ProductInputTO;
import io.github.besi97.product.graphql.model.ProductTO;
import io.github.besi97.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductTO map(Product from);

    @Mapping(target = "id", ignore = true) // auto-generated
    @Mapping(target = "addedDateTime", ignore = true) // this property is set in the service
    Product mapInput(ProductInputTO from);

}
