package io.github.besi97.order.graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.github.besi97.order.graphql.api.OrderByIdQueryResolver;
import io.github.besi97.order.graphql.api.OrdersQueryResolver;
import io.github.besi97.order.graphql.mappers.OrderMapper;
import io.github.besi97.order.graphql.model.OrderTO;
import io.github.besi97.order.model.OrderNotFoundException;
import io.github.besi97.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class QueriesResolver implements OrdersQueryResolver, OrderByIdQueryResolver, GraphQLQueryResolver {

    @Autowired
    private OrderService service;
    @Autowired
    private OrderMapper mapper;

    @Override
    public List<OrderTO> orders() {
        return service.getOrders().stream().map(mapper::map).collect(toList());
    }

    @Override
    public OrderTO orderById(String id) throws OrderNotFoundException {
        return mapper.map(service.getOrderById(id));
    }
}
