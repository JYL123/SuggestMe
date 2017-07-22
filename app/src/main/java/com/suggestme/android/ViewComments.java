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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewComments extends AppCompatActivity {
    ArrayAdapter adapter;
    String  itemShopName;
    String comments;
    int rating,noRaters;
    ListView listView;
    public ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
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
        listView = (ListView) findViewById(R.id.listComment);
       final String getItemName=getIntent().getExtras().getString("itemName");
       final String getShopName=getIntent().getExtras().getString("shopName");
        final String getItemShop=getItemName+getShopName;
       Log.e(getShopName, "get name");

        final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("comments");
        final DatabaseReference rDatabase= FirebaseDatabase.getInstance().getReference("ratings");


        arr = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //listView((Item) dataSnapshot.getValue());
                for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                {
                    Comment item =dataItem.getValue(Comment.class);
                    itemShopName=item.getItemShop();
                    Log.e(itemShopName, "item shop name");
                    Log.e(getItemShop, " from intent");
                    if(getItemShop.equals(itemShopName))
                    {
                        comments=item.getComment();
                      //  rating=item.getCommentRating();
                      //  arr.add("Comments: "+comments+"\n"+"Rating: "+rating);
                        Log.e(comments, "comments");
                        arr.add("Comments: "+comments+"\n");
                        adapter.notifyDataSetChanged();
                  //      break;
                    }
                    else
                    {
                        Log.e("", "something went wrong");
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


        ValueEventListener listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //listView((Item) dataSnapshot.getValue());
                for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                {
                    Rating dataRating =dataItem.getValue(Rating.class);
                    itemShopName=dataRating.getItemShop();
                    Log.e(itemShopName, "item shop name");
                    if(getItemShop.equals(itemShopName))
                    {
                        noRaters=dataRating.getNoOfRaters();
                        rating=dataRating.getRating();
                    }
                    else
                    {
                        Log.e("", "something went wrong");
                    }
                }
                rating = rating/noRaters;
                arr.add("Rating: "+rating);
                Log.e("Rating: "+rating, "debug");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println(databaseError.toException());
                // ...
            }
        };
        rDatabase.addValueEventListener(listener2);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arr);

        listView.setAdapter(adapter);
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
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
