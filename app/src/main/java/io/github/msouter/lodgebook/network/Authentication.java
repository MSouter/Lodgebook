package io.github.msouter.lodgebook.network;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Firebase Authentication Helper
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Authentication {
    private static FirebaseAuth sFirebaseAuth = null;
    private static FirebaseUser sFirebaseUser = null;

    // Initialize the Firebase Auth
    public static void initFirebase() {
        sFirebaseAuth = FirebaseAuth.getInstance();
        sFirebaseUser = sFirebaseAuth.getCurrentUser();
    }

    /**
     * GET
     */
    public static FirebaseAuth getFirebaseInstance() {
        return sFirebaseAuth;
    }
    public static FirebaseUser getFirebaseUser() {
        return sFirebaseUser;
    }

    /**
     * Sign out from Firebase and Facebook Authorization
     */
    public static void signOutFirebase() {
        sFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();
    }
}
