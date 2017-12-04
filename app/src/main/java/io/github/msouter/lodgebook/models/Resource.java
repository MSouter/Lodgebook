package io.github.msouter.lodgebook.models;

/**
 * A Resource belongs to a Lodge and represents
 * anything from food to water to fuel that the
 * users want to track.
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Resource {
    private String mName;
    private String mNote;
    private double mQuantity;
    private Quantities mQuantityType;

    public Resource(String name, String note, double q, Quantities t) {
        this.mName = name;
        this.mNote = note;
        this.mQuantity = q;
        this.mQuantityType = t;
    }

    public String getName() {
        return mName;
    }

    public String getNote() {
        return mNote;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public Quantities getQuantityType() {
        return mQuantityType;
    }
}
