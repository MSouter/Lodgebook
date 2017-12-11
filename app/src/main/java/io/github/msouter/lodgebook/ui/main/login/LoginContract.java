package io.github.msouter.lodgebook.ui.main.login;

import com.facebook.AccessToken;

/**
 * Contract between Login View and Presenter
 * Created by Michael Souter on 2017-12-07.
 */

public interface LoginContract {
    interface View {
        void confirmLogin();

        void displayMessage(String message);
    }

    interface Presenter {
        void loginWithCredentials(String email, String password);

        void loginWithCredentials(AccessToken accessToken);
    }
}
