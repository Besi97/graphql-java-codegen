package io.github.besi97.order.model;

import java.util.Collection;

public class UnableToRetrieveProductsException extends Exception {

    public UnableToRetrieveProductsException(Collection<String> ids, String message) {
        super(String.format("Unable to retrieve products with ids '%s': %s", ids, message));
    }

}
