package com.suggestme.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class changeCredentials extends AppCompatActivity {
    String email;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String name;
    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credentials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();

        final EditText displayEdit=(EditText) findViewById(R.id.DisplayEdit);
        final String getItemArr=getIntent().getExtras().getString("itemArr");
        final String getShopName=getIntent().getExtras().getString("shopName");
        final String getItemName=getIntent().getExtras().getString("itemName");
        //get user email
        final String email = getIntent().getExtras().getString("userEmail").trim();
        Log.e(email, "get email");

        //pull user info according to the email
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        //change name&pwd
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                {
                    User user=dataItem.getValue(User.class);
                    Log.e(user.getEmail(), "database email");
                    if((user.getEmail().trim()).equals(email))
                    {
                        name=user.getUsername();//get username
                        Log.e(name, "get user name");
                        displayEdit.setText("Welcome "+name+" !");
                        pwd=user.getPassword();//get pwd
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println(databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(listener);



        Button changeusername=(Button) findViewById(R.id.changeUserNameButton);
        Button changePwd=(Button) findViewById(R.id.changePwdBtn);
        changeusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(new Intent(changeCredentials.this, changeUsername.class));
                i1.putExtra("username", name);
                i1.putExtra("email", email);
                startActivity(i1);
                finish();
            }
        });

        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(new Intent(changeCredentials.this, changePwd.class));
                i2.putExtra("password", pwd);
                i2.putExtra("email", email);
                i2.putExtra("itemArr", getItemArr);
                i2.putExtra("shopName", getShopName);
                i2.putExtra("itemName", getItemName);



                startActivity(i2);
                finish();
            }
        });
    }
    //app bar
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
