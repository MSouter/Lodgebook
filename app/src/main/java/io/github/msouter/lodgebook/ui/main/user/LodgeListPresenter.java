package io.github.msouter.lodgebook.ui.main.user;

import java.util.ArrayList;

import io.github.msouter.lodgebook.model.Lodge;

/**
 * Presenter for the LodgeListAdapter
 * Created by Michael Souter on 2017-12-11.
 */

public class LodgeListPresenter {
    private ArrayList<Lodge> mData;

    public LodgeListPresenter(ArrayList<Lodge> data) {
        this.mData = data;
    }

    void onBindRepositoryAtPosition(int position, LodgeListAdapter.LodgeViewHolder holder) {
        holder.setLodgeName(mData.get(position).getName());
        holder.setLodgeCount(Integer.toString(mData.get(position).getMembers().size()) + " Members");
        holder.setLodgePic(mData.get(position).getPhotoUrl());
    }

    int getRepositoryCount() {
        return mData.size();
    }

    public void changeData(ArrayList<Lodge> newData) {
        mData = newData;
    }

}
