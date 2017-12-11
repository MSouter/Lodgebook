package io.github.msouter.lodgebook.model;

import java.util.HashMap;

/**
 * Model Class for a User
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class User {
    private String displayName;
    private String emailAddress;
    private String photoUrl;
    private HashMap<String, Boolean> lodges;

    // Default Constructor required for calls to DataSnapshot.getValue(User.class)
    public User() { }

    /**
     * Create a new user with initialized data
     * @param n User's Display Name
     * @param e Email Address
     * @param p URL to a profile photo
     */
    public User(String n, String e, String p) {
        this.displayName = n;
        this.emailAddress = e;
        this.photoUrl = p;
        this.lodges = new HashMap<>();
    }

    /**
     * GET/SET
     * Only the name should be directly modifiable in this manner
     */
    public String getDisplayName() { return this.displayName; }
    public String getEmailAddress() { return this.emailAddress; }
    public String getPhotoUrl() { return this.photoUrl; }
    public HashMap<String, Boolean> getLodges() { return this.lodges; }

    public void setDisplayName(String newName) { this.displayName = newName; }

    /**
     * Add/Remove a Lodge to the HashMap
     *
     * @param lodgeID The ID of the Lodge to be added/removed
     */
    public void addLodge(String lodgeID) {
        if (this.lodges == null) {
            this.lodges = new HashMap<>();
        }
        this.lodges.put(lodgeID, true);
    }
    public void removeLodge(String lodgeID) { this.lodges.remove(lodgeID); }



}
