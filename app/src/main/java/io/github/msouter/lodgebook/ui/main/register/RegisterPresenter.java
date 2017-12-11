package io.github.msouter.lodgebook.ui.main.register;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import io.github.msouter.lodgebook.network.Authentication;

/**
 * Presenter for Register UI View
 * Created by Michael Souter on 2017-12-10.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private static final String TAG = RegisterFragment.class.getSimpleName();

    private final RegisterContract.View mRegisterView;

    RegisterPresenter(RegisterContract.View registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void createAccount(String email, String password) {
        if (password.isEmpty() || email.isEmpty()) {
            return;
        }
        Authentication.getFirebaseInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Account created
                            Log.d(TAG, "createAccount:success");
                            mRegisterView.confirmRegistration();
                        } else {
                            // If registration fails, display a message to the user.
                            Log.w(TAG, "createAccount:failure", task.getException());
                            @SuppressWarnings("ConstantConditions") String message = task.getException().getMessage();
                            mRegisterView.displayMessage(message);
                        }
                    }
                });
    }
}
