package io.github.msouter.lodgebook.network;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Controller for Firebase Authentication.
 * Includes methods required for Facebook sign in and
 * account linkage.
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Authentication {
    private static FirebaseAuth sFirebaseAuth = null;
    private static FirebaseUser sFirebaseUser = null;

    public static void initFirebase() {
        sFirebaseAuth = FirebaseAuth.getInstance();
        sFirebaseUser = sFirebaseAuth.getCurrentUser();
    }

    public static FirebaseAuth getFirebaseInstance() {
        return sFirebaseAuth;
    }

    public static FirebaseUser getFirebaseUser() {
        return sFirebaseUser;
    }

    public static void signOutFirebase() {
        sFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();
    }
}
