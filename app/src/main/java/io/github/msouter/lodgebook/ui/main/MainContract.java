package io.github.msouter.lodgebook.ui.main;

/**
 * Contract between Main View and Presenter
 * Created by Michael Souter on 2017-12-07.
 */

public interface MainContract {

    interface View {
        void startLogin();

        void startUser();
    }

    interface Presenter {
        void start();
    }
}
