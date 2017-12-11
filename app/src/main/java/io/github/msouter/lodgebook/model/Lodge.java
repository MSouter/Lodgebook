package io.github.msouter.lodgebook.model;

import java.util.HashMap;

/**
 * Model Class for Lodge/Cabin/Cottage/ETC
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Lodge {
    private String name;
    private String description;
    private HashMap<String, Boolean> members;

    // Default Constructor required for calls to DataSnapshot.getValue(Lodge.class)
    public Lodge() { }

    /**
     * Create a new lodge with a name and the current UserID
     * as the first member.
     *
     * @param newName String representing the new Lodge's name
     * @param u       The user creating this lodge
     */
    public Lodge(String newName, String u) {
        this.name = newName;
        this.description = " "; // Left blank initially
        this.members = new HashMap<>();
        this.members.put(u, true);
    }

    /**
     * GET/SET
     * Name and Description are directly modifiable in this manner
     */
    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public HashMap<String, Boolean> getMembers() { return this.members; }

    public void setName(String newName) { this.name = newName; }
    public void setDescription(String newDescription) { this.description = newDescription; }

    /**
     * Add/Remove a User from the members HashMap
     *
     * @param u UserID to add/remove
     */
    public void addMember(String u) { members.put(u, true); }
    public void removeMember(String u) { members.remove(u); }
}
