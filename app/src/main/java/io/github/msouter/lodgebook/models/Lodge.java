package io.github.msouter.lodgebook.models;

import java.util.ArrayList;

/**
 * A lodge/cottage/cabin/etc
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Lodge {
    private String mName;
    private ArrayList<Member> mMembers;
    private ArrayList<Resource> mResources;
    private ArrayList<Container> mContainers;
}
