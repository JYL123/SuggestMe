package com.suggestme.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changePwd extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
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
        //get email and pwd
        final String email = getIntent().getExtras().getString("email");
        final String pwd = getIntent().getExtras().getString("pwd");
        final Intent intentSignout=new Intent(changePwd.this, LoginActivity.class);
        final String getItemArr=getIntent().getExtras().getString("itemArr");
        final String getShopName=getIntent().getExtras().getString("shopName");
        final String getItemName=getIntent().getExtras().getString("itemName");

        intentSignout.putExtra("itemArr", getItemArr);
        intentSignout.putExtra("shopName", getShopName);
        intentSignout.putExtra("itemName", getItemName);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Log.e("", "sign out with user=null");
                    startActivity(intentSignout);

                    finish();
                }
                else
                {
                    Log.e("", "sign out with user!=null");
                    startActivity(intentSignout);
                    finish();
                }
            }
        };

        //initializa button to change pwd
        Button button1=(Button) findViewById(R.id.updateBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get texts
                EditText text1=(EditText) findViewById(R.id.enterPwdTxt);
                EditText text2=(EditText) findViewById(R.id.enterPwdTxt2);
                final String result1 = text1.getText().toString();
                final String result2 = text2.getText().toString();

                if(result1.equals(result2))
                {
                    //connect to database
                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    //change name&pwd
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                            {
                                User userDatabase=dataItem.getValue(User.class);
                                if(userDatabase.getEmail().equals(email))
                                {
                                    user.updatePassword(result1);
                                    dataItem.child("password").getRef().setValue(result1);
                                    Toast.makeText(changePwd.this, "Password is updated, sign in with new password!", Toast.LENGTH_LONG).show();
                                    signOut();
                                    Log.e("", "sign out from intent");
                                    startActivity(intentSignout);
                                    finish();
                                    break;

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
                }
                else
                {
                    TextView error=(TextView) findViewById(R.id.errorTxt);
                    error.setText("The passwords are not the same");
                }

            }
        });



    }


    public void signOut() {
        auth.signOut();
    }

}


