package io.github.besi97.order.model;

public class UnableToCreateProductException extends Exception {

    public UnableToCreateProductException(String message) {
        super(String.format("Unable to create product: %s", message));
    }

}
