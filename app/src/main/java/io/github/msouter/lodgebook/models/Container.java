package io.github.msouter.lodgebook.models;

import java.util.ArrayList;

/**
 * A Container holds resources at a Lodge.
 * This could be a Fridge, Pantry, etc...
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Container {
    private String mName;
    private ArrayList<Resource> mContents;

    public Container(String name, ArrayList<Resource> contents) {
        this.mName = name;
        this.mContents = contents;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Resource> getContents() {
        return mContents;
    }
}
