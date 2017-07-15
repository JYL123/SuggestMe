package com.suggestme.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewComments extends AppCompatActivity {

    String getItemName=getIntent().getExtras().getString("itemName");
    String getShopName=getIntent().getExtras().getString("shopName");
    ArrayAdapter adapter;
    String  shopName;
    String comments;
    int rating;
    ListView listView = (ListView) findViewById(R.id.listComment);
    public ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
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

        final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("comments");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //listView((Item) dataSnapshot.getValue());
                for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                {
                    Comment item =dataItem.getValue(Comment.class);
                    shopName=item.getShopName();

                    if(shopName.equalsIgnoreCase(getShopName))
                    {
                        comments=item.getComment();
                        rating=item.getCommentRating();
                        arr.add("Comments: "+comments+"\n"+"Rating: "+rating);
                        adapter.notifyDataSetChanged();
                        break;
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
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arr);

        listView.setAdapter(adapter);
    }

}
