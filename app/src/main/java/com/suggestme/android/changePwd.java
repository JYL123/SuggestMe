package com.suggestme.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changePwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //get email and pwd
        final String email = getIntent().getExtras().getString("email");
        final String pwd = getIntent().getExtras().getString("pwd");
        //initializa button to change pwd
        Button button1=(Button) findViewById(R.id.updateBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get texts
                EditText text1=(EditText) findViewById(R.id.enterPwdTxt);
                EditText text2=(EditText) findViewById(R.id.enterPwdTxt2);
                String result1 = text1.getText().toString();
                String result2 = text2.getText().toString();

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
                                User user=dataItem.getValue(User.class);
                                if(user.getEmail()==email)
                                {
                                    //update pwd in the database
                                    user.setPassword(pwd);
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

}
