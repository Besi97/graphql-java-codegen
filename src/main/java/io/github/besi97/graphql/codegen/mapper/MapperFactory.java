package io.github.besi97.graphql.codegen.mapper;

/**
 * Factory for creating JVM-language-specific mappers
 *
 * @author Besi97
 */
public interface MapperFactory {

    DataModelMapper getDataModelMapper();

    GraphQLTypeMapper getGraphQLTypeMapper();

    AnnotationsMapper getAnnotationsMapper();

    ValueMapper getValueMapper();

}
