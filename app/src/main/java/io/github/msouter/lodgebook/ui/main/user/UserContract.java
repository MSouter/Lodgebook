package io.github.msouter.lodgebook.ui.main.user;

import java.util.ArrayList;

/**
 * Contract between User View and Presenter
 * Created by Michael Souter on 2017-12-08.
 */

public interface UserContract {
    interface View {
        void updateUser(String name, String email, String photo);

        void updateLodgeList();

        void viewLodge(String lodgeId);

        void toggleProgressBar();
    }

    interface Presenter {
        void startListeners(LodgeListPresenter lodgeListPresenter);

        void stopListeners();

        void addLodge(String lodgeName);

        void viewLodge(int position);

        void signOut();
    }
}
