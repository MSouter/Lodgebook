package io.github.msouter.lodgebook.models;

import android.net.Uri;

/**
 * A Member is a user who belongs to a Lodge.
 * We only need to track the other users ID,
 * DisplayName, and PhotoUrl
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Member {
    private String mDisplayName;
    private String mID;
    private Uri mPhotoUrl;

    public Member(String name, String id, Uri photo) {
        this.mDisplayName = name;
        this.mID = id;
        this.mPhotoUrl = photo;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public String getID() {
        return this.mID;
    }

    public Uri getPhotoUrl() {
        return this.mPhotoUrl;
    }
}
