package io.github.msouter.lodgebook.ui.main;

import io.github.msouter.lodgebook.network.Authentication;
import io.github.msouter.lodgebook.network.RemoteDB;

/**
 * Presenter for Main UI View
 * Created by Michael Souter on 2017-12-07.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mMainView;

    MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
    }

    @Override
    public void start() {
        Authentication.initFirebase();
        RemoteDB.initDatabase();

        if (Authentication.getFirebaseUser() == null) {
            mMainView.startLogin();
        } else {
            mMainView.startUser();
        }
    }
}
