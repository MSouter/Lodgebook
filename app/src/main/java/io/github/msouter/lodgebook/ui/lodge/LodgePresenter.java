package io.github.msouter.lodgebook.ui.lodge;

/**
 * Created by Michael Souter on 2017-12-10.
 */

public class LodgePresenter implements LodgeContract.Presenter {
    private LodgeContract.View mLodgeView;

    LodgePresenter(LodgeContract.View lodgeView) {
        mLodgeView = lodgeView;
    }

    @Override
    public void start(String lodgeId) {

    }
}
