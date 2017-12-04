package io.github.msouter.lodgebook.network;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.msouter.lodgebook.models.User;

/**
 * Handle interactions with the database
 *
 * Created by Michael Souter on 2017-12-03.
 */

public class Database {
    private static DatabaseReference sDatabase;
    private static DatabaseReference sMyRef;
    private static ValueEventListener valueEventListener;

    public static void initDatabase() {
        sDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static void getUser(final UpdateCallback updateCallback) {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateCallback.updateData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        sMyRef = sDatabase.child("users").child(Authentication.getFirebaseUser().getUid());
        sMyRef.addValueEventListener(valueEventListener);
    }

    public static void setUser(User user) {
        sDatabase.child("users").child(Authentication.getFirebaseUser().getUid()).setValue(user);
    }

    public static void clearListeners() {
        if (sMyRef!=null && valueEventListener!=null) {
            sMyRef.removeEventListener(valueEventListener);
        }
    }
}
