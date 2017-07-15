package com.suggestme.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.suggestme.android.MainActivity.MY_PREFS_NAME;

public class CommentActivity extends AppCompatActivity {
    private Button submitBtn, creditBtn;
    private EditText commentTxt;
    private TextView creditView;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        creditBtn = (Button) findViewById(R.id.creditBtn);
        commentTxt = (EditText) findViewById(R.id.commentTxt);
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comments");
        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();

        final Query userQuery = userDatabase.child("users").orderByChild("email").equalTo(user.getEmail());

//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        final String username = prefs.getString("text", null);
//        if (username != null) {
//            String name = prefs.getString("username", "No name defined");//"No name defined" is the default value.
//        }
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

        submitBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                final String comment = commentTxt.getText().toString().trim();

                if (user != null && !comment.equals("")) {

                    final String getShopName=getIntent().getExtras().getString("shopName");
                    final String getItemName=getIntent().getExtras().getString("itemName");

                            String commentId = mDatabase.push().getKey();
                            Comment newComment = new Comment(comment, 0, getShopName, getItemName,username, getItemName+getShopName);
                            mDatabase.child(commentId).setValue(newComment);
                            showInputDialog();

                        } else if (comment.equals("")) {
                            commentTxt.setError("Enter comment");
                        }
                    }
        });

        creditBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {


                showCreditDialog();
            }
        });

    }

    protected void showInputDialog() {

        // get prompts.xml view


        LayoutInflater layoutInflater = LayoutInflater.from(CommentActivity.this);
        View promptView = layoutInflater.inflate(R.layout.comment_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CommentActivity.this,R.style.AlertDialogTheme);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
       .setPositiveButton("Back to home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(CommentActivity.this, welcomeActivity.class));
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

    protected void showCreditDialog() {

        // get prompts.xml view


        LayoutInflater layoutInflater = LayoutInflater.from(CommentActivity.this);
        View promptView = layoutInflater.inflate(R.layout.credit_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CommentActivity.this,R.style.AlertDialogTheme);
        alertDialogBuilder.setView(promptView);


        creditView = (TextView) promptView.findViewById(R.id.creditView);
     //   creditView.setText("hello");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


        final Query userQuery = mDatabase.child("users").orderByChild("email").equalTo(user.getEmail());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                        User currentUser = dataSnapshot.getValue(User.class);
//                System.out.println(currentUser);
//                        System.out.println("hello");
              //  String credit="";
                int credit=0;
                   for (DataSnapshot userRatingSnapshot : dataSnapshot.getChildren()) {
                       User currUser=userRatingSnapshot.getValue(User.class);
                       credit=currUser.getUserRating();
                   //   credit = (String) userRatingSnapshot.child("username").getValue();

//                //     String username = usernameSnapshot.getKey();
               //  User credit= userRatingSnapshot.child("userRating").getValue(User.class);
                     // creditView.setText(dataSnapshot.child("userRating").getValue());
////
                }
                creditView.setText(Integer.toString(credit));
//                Log.d("TEST","key: "+dataSnapshot.getKey());
//                Log.d("TEST","value: "+dataSnapshot.getValue());
                //  User currentUser= dataSnapshot.getValue(User.class);
              //  int credit=currentUser.getUserRating();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)

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
    //}



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
            case R.id.settings:
                startActivity(new Intent(this, UserSettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
