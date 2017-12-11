package io.github.msouter.lodgebook.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.msouter.lodgebook.R;
import io.github.msouter.lodgebook.ui.main.login.LoginFragment;
import io.github.msouter.lodgebook.ui.main.user.UserFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;

    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (android.support.v4.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void startLogin() {
        LoginFragment fragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), fragment).commit();
    }

    @Override
    public void startUser() {
        UserFragment fragment = new UserFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), fragment).commit();
    }
}
