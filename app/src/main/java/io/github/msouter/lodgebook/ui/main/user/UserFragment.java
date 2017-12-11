package io.github.msouter.lodgebook.ui.main.user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.msouter.lodgebook.R;
import io.github.msouter.lodgebook.utils.GlideApp;

/**
 * UI View for a User
 */
public class UserFragment extends Fragment implements UserContract.View {
    private UserContract.Presenter presenter;
    private UserActionListener mCallbackUserAction;

    public interface UserActionListener {
        void onSignOut();
        void onLodgeSelect(String lodgeId);
    }

    @BindView(R.id.tv_displayname) TextView tvDisplayName;
    @BindView(R.id.tv_email) TextView tvEMail;
    @BindView(R.id.iv_userpic) ImageView ivUserpic;
    @BindView(R.id.recycler_view_lodges) RecyclerView recyclerViewLodges;
    @BindView(R.id.prog_loading_user) ProgressBar progLoadingUser;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new UserPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);


        recyclerViewLodges.setHasFixedSize(true);
        recyclerViewLodges.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbackUserAction = (UserActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UserActionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                presenter.signOut();
                mCallbackUserAction.onSignOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LodgeListPresenter lodgeListPresenter = new LodgeListPresenter(null);
        presenter.startListeners(lodgeListPresenter);
        LodgeListAdapter adapter = new LodgeListAdapter(lodgeListPresenter);
        recyclerViewLodges.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopListeners();
    }

    @Override
    public void updateUser(String name, String email, String photo) {
        tvDisplayName.setText(name);
        tvEMail.setText(email);
        GlideApp.with(this)
                .load(photo)
                .placeholder(R.drawable.user_icon)
                .circleCrop()
                .into(ivUserpic);
    }

    @Override
    public void updateLodgeList() {
        recyclerViewLodges.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void viewLodge(String lodgeId) {
        mCallbackUserAction.onLodgeSelect(lodgeId);
    }

    @Override
    public void toggleProgressBar() {
        if (progLoadingUser.getVisibility() == View.GONE) {
            progLoadingUser.setVisibility(View.VISIBLE);
        } else {
            progLoadingUser.setVisibility(View.GONE);
        }
    }

    @SuppressLint("InflateParams")
    @OnClick(R.id.btn_add_lodge)
    void addLodge() {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.dialog_create_lodge, null))
                    .setPositiveButton(R.string.save_name, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Dialog d = (Dialog) dialog;
                            presenter.addLodge(((EditText) d.findViewById(R.id.et_lodge_name))
                                    .getText().toString());
                        }
                    })
                    .setNegativeButton(R.string.cancel_name, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
