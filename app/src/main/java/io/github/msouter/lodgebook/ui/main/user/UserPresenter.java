package io.github.msouter.lodgebook.ui.main.user;

import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.msouter.lodgebook.model.Lodge;
import io.github.msouter.lodgebook.model.User;
import io.github.msouter.lodgebook.network.Authentication;
import io.github.msouter.lodgebook.network.RemoteDB;

/**
 * Presenter for User UI View
 * Created by Michael Souter on 2017-12-08.
 */

public class UserPresenter implements UserContract.Presenter {
    private final UserContract.View mUserView;

    private User mUser;
    private HashMap<String, String> mLodgeList;
    private DatabaseReference mListReference;
    private ChildEventListener mListListener;

    UserPresenter(UserContract.View userView) {
        mUserView = userView;
        mUser = new User();
    }

    @Override
    public void start() {
        mUserView.showProgressBar();
        RemoteDB.getReferenceFor(mUser, Authentication.getFirebaseUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mUser = dataSnapshot.getValue(User.class);
                            mUserView.showProgressBar();
                            mUserView.updateUser(mUser.getDisplayName(),
                                    mUser.getEmailAddress(),
                                    mUser.getPhotoUrl());
                            updateLodgeList();
                        } else {
                            createUserFromAuth();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        mListReference = (RemoteDB.getReferenceFor(mUser, Authentication.getFirebaseUser().getUid())).child("lodges");
        mListListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mUser.addLodge(dataSnapshot.getKey());
                updateLodgeList();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mUser.removeLodge(dataSnapshot.getKey());
                updateLodgeList();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mListReference.addChildEventListener(mListListener);
    }

    @Override
    public void pause() {
        mListReference.removeEventListener(mListListener);
    }

    @Override
    public void addLodge(String lodgeName) {
        // Update objects
        Lodge newLodge = new Lodge(lodgeName, Authentication.getFirebaseUser().getUid());
        String lodgeID = RemoteDB.createIdFor(newLodge);
        mUser.addLodge(lodgeID);

        // Post to remote
        RemoteDB.getReferenceFor(newLodge, lodgeID).setValue(newLodge);
        RemoteDB.getReferenceFor(mUser, Authentication.getFirebaseUser().getUid())
                .child("lodges").child(lodgeID).setValue(true);
    }

    @Override
    public void viewLodge(String lodgeName) {

    }

    @Override
    public void signOut() {
        Authentication.signOutFirebase();
    }

    private void createUserFromAuth() {
        FirebaseUser firebaseUser = Authentication.getFirebaseUser();

        String email = firebaseUser.getEmail();

        String name;
        if (firebaseUser.getDisplayName() == null) {
            assert email != null;
            name = email.substring(0, email.indexOf("@"));
        } else {
            name = firebaseUser.getDisplayName();
        }

        String photo;
        if (firebaseUser.getPhotoUrl() == null) {
            photo = "";
        } else {
            photo = firebaseUser.getPhotoUrl().toString();
            for (UserInfo info : firebaseUser.getProviderData()) {
                if (info.getProviderId().equals(FacebookAuthProvider.PROVIDER_ID)) {
                    photo = "https://graph.facebook.com/" + info.getUid() + "/picture?type=large";
                }
            }
        }

        mUser = new User(name, email, photo);
        RemoteDB.setValue(RemoteDB.getReferenceFor(mUser, firebaseUser.getUid()), mUser);
        mUserView.showProgressBar();
        mUserView.updateUser(mUser.getDisplayName(),
                mUser.getEmailAddress(),
                mUser.getPhotoUrl());
    }

    private void updateLodgeList() {
        mLodgeList = new HashMap<>();
        Lodge pathFinder = new Lodge();

        HashMap<String, Boolean> lodges = mUser.getLodges();
        if (lodges == null) {
            return;
        }
        for (Map.Entry<String, Boolean> entry : lodges.entrySet()) {
            final String thisKey = entry.getKey();
            RemoteDB.getReferenceFor(pathFinder, thisKey)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String lodgeName = dataSnapshot.child("name").getValue(String.class);
                            mLodgeList.put(thisKey, lodgeName);
                            sendUpdatedList();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    private void sendUpdatedList() {
        ArrayList<String> lodgeNames = new ArrayList<>();
        lodgeNames.addAll(mLodgeList.values());
        mUserView.updateLodgeList(lodgeNames);
    }
}
