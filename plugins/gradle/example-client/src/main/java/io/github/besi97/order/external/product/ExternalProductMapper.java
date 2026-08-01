package io.github.besi97.order.external.product;

import io.github.besi97.order.model.Product;
import io.github.besi97.product.graphql.model.ProductTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExternalProductMapper {

    Product map(ProductTO from);

}
