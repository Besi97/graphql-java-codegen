package io.github.besi97.order.graphql.mappers;

import io.github.besi97.order.graphql.model.ItemTO;
import io.github.besi97.order.graphql.model.OrderTO;
import io.github.besi97.order.model.Item;
import io.github.besi97.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderTO map(Order from);

    ItemTO map(Item from);

}
