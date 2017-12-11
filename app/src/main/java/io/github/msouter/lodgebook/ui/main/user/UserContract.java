package io.github.msouter.lodgebook.ui.main.user;

import java.util.ArrayList;

/**
 * Contract between User View and Presenter
 * Created by Michael Souter on 2017-12-08.
 */

public interface UserContract {
    interface View {
        void updateUser(String name, String email, String photo);

        void updateLodgeList(ArrayList<String> lodges);

        void viewLodge(String lodgeId);

        void showProgressBar();
    }

    interface Presenter {
        void start();

        void pause();

        void addLodge(String lodgeName);

        void viewLodge(String lodgeName);

        void signOut();
    }
}
