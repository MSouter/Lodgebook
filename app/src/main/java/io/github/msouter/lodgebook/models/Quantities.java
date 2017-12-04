package io.github.msouter.lodgebook.models;

/**
 * Types of quantities
 * Created by Michael Souter on 2017-12-03.
 */

public enum Quantities {
    PERCENT("Percent"),
    AMOUNT("Amount")
    ;

    private final String text;

    private Quantities(final String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
