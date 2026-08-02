package io.github.besi97.graphql.codegen.model.exception;

/**
 * Exception that indicates error while deleting directory
 *
 * @author Besi97
 */
public class UnableToDeleteDirectoryException extends RuntimeException {

    public UnableToDeleteDirectoryException(Exception e) {
        super("Unable to delete directory", e);
    }

}
