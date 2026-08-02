package io.github.besi97.graphql.codegen.model.exception;

/**
 * Exception that indicates error while creating a file
 *
 * @author Besi97
 */
public class UnableToCreateFileException extends RuntimeException {

    public UnableToCreateFileException(Exception e) {
        super("Unable to create file", e);
    }

}
