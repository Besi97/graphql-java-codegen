package io.github.besi97.graphql.codegen.model.exception;

/**
 * Exception that indicates invalid GraphQL schema
 *
 * @author Besi97
 */
public class SchemaValidationException extends RuntimeException {

    public SchemaValidationException(String message) {
        super("GraphQL schema is invalid: " + message);
    }

}
