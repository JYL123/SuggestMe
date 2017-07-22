package com.suggestme.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RateActivity extends AppCompatActivity {

    private Button submitBtn;
    private RatingBar ratingBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public String username;
    public int currRating;
    public int currRaters;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        auth = FirebaseAuth.getInstance();

        final String getItemArr=getIntent().getExtras().getString("itemArr");
        final String getShopName=getIntent().getExtras().getString("shopName");
        final String getItemName=getIntent().getExtras().getString("itemName");

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("ratings");
        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();

        final Query userQuery = userDatabase.child("users").orderByChild("email").equalTo(user.getEmail());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usernameSnapshot : dataSnapshot.getChildren()) {
                    //     String username = usernameSnapshot.getKey();
                    User currUser=usernameSnapshot.getValue(User.class);
                    username=currUser.getUsername();
                    Log.e("Username" ,""+ username);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });

        final Query ratingQuery = userDatabase.child("ratings").orderByChild("itemShop").equalTo(getItemName+getShopName);

        ratingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count=0;
                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    //     String username = usernameSnapshot.getKey();
                    count++;
                    Rating value=ratingSnapshot.getValue(Rating.class);
                    currRating=value.getRating();
                    currRaters=value.getNoOfRaters();
                    Log.e("Current Rating" ,""+ currRating);
                    Log.e("Current Raters" ,""+ currRaters);
                }
                if(count==0){
                    currRating=0;
                    currRaters=0;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Rating", databaseError.getMessage());
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final int rating = (int)ratingBar.getRating();
                currRaters++;
                if (user != null) {
                    String commentId = mDatabase.push().getKey();
                    Rating newRating = new Rating(rating+currRating, getShopName, getItemName, username,currRaters,getItemName+getShopName);
                    mDatabase.child(commentId).setValue(newRating);
                    showInputDialog();
                }
            }
        });
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(RateActivity.this);
        View promptView = layoutInflater.inflate(R.layout.rate_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RateActivity.this,R.style.AlertDialogTheme);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Back to home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(RateActivity.this, welcomeActivity.class));
                        finish();
                    }
                })

                .setNegativeButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, welcomeActivity.class));
                return true;
            case R.id.out:
                signOut();
                Toast.makeText(getApplicationContext(), "You have signed out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.back:
                //startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void signOut() {
        auth.signOut();
    }

}
