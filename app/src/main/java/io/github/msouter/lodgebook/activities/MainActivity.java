package io.github.msouter.lodgebook.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import io.github.msouter.lodgebook.R;
import io.github.msouter.lodgebook.models.User;
import io.github.msouter.lodgebook.network.Authentication;
import io.github.msouter.lodgebook.network.Database;
import io.github.msouter.lodgebook.network.UpdateCallback;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        Authentication.initFirebase();

        if (Authentication.getFirebaseUser() == null) {
            loadLogin();
        } else {
            init();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ActionBar clicks
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Authentication.signOutFirebase();
            loadLogin();
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        Database.initDatabase();

        final User user = new User(Authentication.getFirebaseUser());

        // Prompt to create Display Name
        if ("".equals(user.getDisplayName())) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View promptView = inflater.inflate(R.layout.layout_displayname_prompt, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptView);
            final EditText userInput = promptView.findViewById(R.id.et_displayname);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    if (userInput.getText().toString().equals("")) {
                                        String email = user.getEmailAddress();
                                        int index = email.indexOf('@');
                                        String newName = email.substring(0, index);
                                        user.setDisplayName(newName);
                                    }
                                    user.setDisplayName(userInput.getText().toString());
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    String email = user.getEmailAddress();
                                    int index = email.indexOf('@');
                                    String newName = email.substring(0, index);
                                    user.setDisplayName(newName);
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        Database.setUser(user);

        Database.clearListeners();
        Database.getUser(new UpdateCallback() {
            @Override
            public void updateData(DataSnapshot data) {
                refreshData(data);
            }
        });
    }

    private void refreshData(DataSnapshot data) {
        User myUser = data.getValue(User.class);
        TextView tvDisplayname = findViewById(R.id.tv_displayname);
        TextView tvEmail = findViewById(R.id.tv_email);
        tvDisplayname.setText(myUser.getDisplayName());
        tvEmail.setText(myUser.getEmailAddress());

        LinearLayout llPhotoLayout = findViewById(R.id.linear_layout_userpic_container);
        ImageView tvPhoto = findViewById(R.id.iv_userpic);
        if (myUser.getPhotoUrl().equals("")) {
            Picasso.with(this)
                    .load(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .resize(llPhotoLayout.getWidth() / 2, llPhotoLayout.getWidth() / 2)
                    .transform(new RoundedCornersTransformation(40, 5))
                    .centerCrop()
                    .into(tvPhoto);
        } else {
            Picasso.with(this)
                    .load(myUser.getPhotoUrl())
                    .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .resize(llPhotoLayout.getWidth() / 2, llPhotoLayout.getWidth() / 2)
                    .transform(new RoundedCornersTransformation(40, 5))
                    .centerCrop()
                    .into(tvPhoto);
        }

    }

    private void loadLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
