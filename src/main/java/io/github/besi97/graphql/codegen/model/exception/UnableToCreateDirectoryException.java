package io.github.besi97.graphql.codegen.model.exception;

/**
 * Exception that indicates error while creating directory
 *
 * @author Besi97
 */
public class UnableToCreateDirectoryException extends RuntimeException {

    public UnableToCreateDirectoryException(String directoryPath, Exception e) {
        super("Unable to create directory by path: " + directoryPath, e);
    }

}
