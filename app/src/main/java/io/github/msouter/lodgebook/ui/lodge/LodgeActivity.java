package io.github.msouter.lodgebook.ui.lodge;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import io.github.msouter.lodgebook.R;

public class LodgeActivity extends AppCompatActivity implements LodgeContract.View {
    private LodgeContract.Presenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodge);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setTitle("Redneck Lodge");

        setSupportActionBar(toolbar);

        presenter = new LodgePresenter(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            presenter.start(extras.getString("lodgeId"));
        } else {
            // No lodge to start with
        }
    }
}
