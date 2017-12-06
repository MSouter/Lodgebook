package io.github.msouter.lodgebook.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

import io.github.msouter.lodgebook.network.Authentication;

/**
 * A user of the Lodgebook App
 * Changes to attributes of the user cause changes
 * to the FirebaseUser
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class User {
    private static final String TAG = "User Class";

    private String displayName;
    private String emailAddress;
    private String photoUrl;

    public User() {
        // Default Constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(FirebaseUser u) {
        this.emailAddress = u.getEmail();

        if (u.getDisplayName()==null) {
            changeDisplayName(emailAddress.substring(0, emailAddress.indexOf("@")));
        } else {
            this.displayName = u.getDisplayName();
        }

        if (u.getPhotoUrl()==null) {
            this.photoUrl = "";
        } else {
            this.photoUrl = u.getPhotoUrl().toString();
            for (UserInfo info : u.getProviderData()) {
                if (info.getProviderId().equals(FacebookAuthProvider.PROVIDER_ID)) {
                    this.photoUrl = "https://graph.facebook.com/" + info.getUid() + "/picture?type=large";
                }
            }
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void changeDisplayName(String name) {
        this.displayName = name;


        FirebaseUser user = Authentication.getFirebaseUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
}
