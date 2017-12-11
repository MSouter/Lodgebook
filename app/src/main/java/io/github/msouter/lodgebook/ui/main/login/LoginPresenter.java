package io.github.msouter.lodgebook.ui.main.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

import io.github.msouter.lodgebook.network.Authentication;

/**
 * Presenter for Login UI View
 * Created by Michael Souter on 2017-12-07.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private final LoginContract.View mLoginView;

    LoginPresenter(LoginContract.View loginView) { mLoginView = loginView; }

    @Override
    public void loginWithCredentials(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            String message = "E-Mail/Password must not be blank!";
            mLoginView.displayMessage(message);
            return;
        }
        Authentication.getFirebaseInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "loginWithCredentials:success");
                            Authentication.initFirebase();
                            mLoginView.confirmLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "loginWithCredentials:failure", task.getException());
                            @SuppressWarnings("ConstantConditions") String message = task.getException().getMessage();
                            mLoginView.displayMessage(message);
                        }
                    }
                });
    }

    @Override
    public void loginWithCredentials(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Authentication.getFirebaseInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Authentication.initFirebase();
                            mLoginView.confirmLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            @SuppressWarnings("ConstantConditions") String message = task.getException().getMessage();
                            mLoginView.displayMessage(message);
                        }
                    }
                });
    }
}
