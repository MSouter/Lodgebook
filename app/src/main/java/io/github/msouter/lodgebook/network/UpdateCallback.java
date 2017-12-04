package io.github.msouter.lodgebook.network;

import com.google.firebase.database.DataSnapshot;

/**
 * Callback function for updating data on views
 *
 * Created by Michael Souter on 2017-12-03.
 */

public interface UpdateCallback {
    void updateData(DataSnapshot data);
}
