package io.github.besi97.graphql.codegen.model.exception;

/**
 * Exception that indicates error while loading Apache FreeMarker template
 *
 * @author Besi97
 */
public class UnableToLoadFreeMarkerTemplateException extends RuntimeException {

    public UnableToLoadFreeMarkerTemplateException(Throwable e) {
        super("Unable to load FreeMarker templates", e);
    }

}
