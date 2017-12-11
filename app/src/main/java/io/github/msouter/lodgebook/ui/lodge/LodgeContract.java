package io.github.msouter.lodgebook.ui.lodge;

/**
 * Contract between Lodge View and Presenter
 * Created by Michael Souter on 2017-12-10.
 */

public interface LodgeContract {
    interface View {

    }

    interface Presenter {
        void start(String lodgeId);
    }
}
