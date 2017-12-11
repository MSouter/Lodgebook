package io.github.msouter.lodgebook.ui.main.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.msouter.lodgebook.R;
import io.github.msouter.lodgebook.utils.GlideApp;

/**
 * View Adapter for Lodges in the User Fragment
 * Created by Michael Souter on 2017-12-11.
 */

public class LodgeListAdapter extends RecyclerView.Adapter<LodgeListAdapter.LodgeViewHolder> {
    private LodgeListPresenter presenter;

    public LodgeListAdapter(LodgeListPresenter p) {
        this.presenter = p;
    }

    @Override
    public LodgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_lodge, parent, false);
        return new LodgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LodgeViewHolder holder, int position) {
        presenter.onBindRepositoryAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepositoryCount();
    }

    static class LodgeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_lodge_name) TextView   mLodgeName;
        @BindView(R.id.tv_lodge_count) TextView  mLodgeCount;
        @BindView(R.id.iv_lodge_pic)  ImageView  mLodgePic;

        LodgeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setLodgeName(String name) {
            mLodgeName.setText(name);
        }

        void setLodgeCount(String count) { mLodgeCount.setText(count); }

        void setLodgePic(String picUrl) {
            if (picUrl==null || picUrl.equals("")) {
                return;
            }
            GlideApp.with(itemView.getContext())
                    .load(picUrl)
                    .placeholder(R.drawable.cottage)
                    .into(mLodgePic);
        }
    }


}
