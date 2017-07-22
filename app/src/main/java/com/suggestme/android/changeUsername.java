package com.suggestme.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class changeUsername extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        auth = FirebaseAuth.getInstance();
        final String email = getIntent().getExtras().getString("email");
        final String username = getIntent().getExtras().getString("username");
        Log.e(email+ " "+username, "email and user name from change credentials");

        Button button1=(Button) findViewById(R.id.updateNameBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get texts
                EditText text1=(EditText) findViewById(R.id.enterNameTxt);
                final String result1 = text1.getText().toString().trim();

                if(!result1.equals(username))
                {
                    //connect to database
                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    //change name&pwd
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                            {

                                User user=dataItem.getValue(User.class);
                                if(user.getEmail().equals(email))
                                {
                                    dataItem.child("username").getRef().setValue(result1);
                                    //update username in the database
                                    Log.e(result1, "new user name");
                                    user.setUsername(result1);
                                }
                            }
                            Toast.makeText(changeUsername.this, "User name is updated!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            System.out.println(databaseError.toException());
                            // ...
                        }
                    };
                    mDatabase.addValueEventListener(listener);
                }
                else
                {
                    TextView error=(TextView) findViewById(R.id.errorNameTxt);
                    error.setText("Something went wrong. Please enter another name");
                }

            }
        });

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
            case R.id.back:
                //startActivity(new Intent(this, changeCredentials.class));
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
