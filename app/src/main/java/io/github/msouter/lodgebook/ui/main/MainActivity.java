package io.github.msouter.lodgebook.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.msouter.lodgebook.R;
import io.github.msouter.lodgebook.ui.lodge.LodgeActivity;
import io.github.msouter.lodgebook.ui.main.login.LoginFragment;
import io.github.msouter.lodgebook.ui.main.user.UserFragment;

/**
 * MainActivity View
 * Serves fragments for Login or User View depending on Auth status
 */
public class MainActivity extends AppCompatActivity implements MainContract.View,
        LoginFragment.OnLoginSuccessListener, UserFragment.UserActionListener {
    private MainContract.Presenter presenter;

    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
    }

    /**
     * onActivityResult required for Facebook Login
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (android.support.v4.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void startLogin() {
        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), fragment).commit();
    }

    @Override
    public void startUser() {
        UserFragment fragment = UserFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), fragment).commit();
    }

    @Override
    public void onLoginSuccess() {
        startUser();
    }

    @Override
    public void onSignOut() {
        startLogin();
    }

    @Override
    public void onLodgeSelect(String lodgeId) {
        Intent intent = new Intent(this, LodgeActivity.class);
        intent.putExtra("lodgeId", lodgeId);
        startActivity(intent);
    }
}
