package io.github.msouter.lodgebook.ui.main.register;

/**
 * Contract between Register View and Presenter
 * Created by Michael Souter on 2017-12-10.
 */

public interface RegisterContract {
    interface View {
        void confirmRegistration();

        void displayMessage(String message);
    }

    interface Presenter {
        void createAccount(String email, String password);
    }
}
