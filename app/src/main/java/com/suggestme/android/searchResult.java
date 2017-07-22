package com.suggestme.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.R.attr.bitmap;

public class searchResult extends AppCompatActivity {
   public ArrayAdapter adapter;
    public ArrayList<String> arr;
    String itemInfoName;
    String itemInfoShop;
    String itemInfoDescription;
    String itemURL;
    public ListView listView;
    Intent i;
    public HashMap<String, String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.listView1);

        TextView tv = (TextView) findViewById(R.id.textView1);
        final String getItemName=getIntent().getExtras().getString("itemName");
        tv.setText("Shops for "+getItemName+" :");
        i = new Intent(searchResult.this, itemDetails.class);
        i.putExtra("itemName", getItemName);

        //select the table
        final DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("items");

        // Attach the adapter to a ListView
        arr = new ArrayList<>();
        urlList=new HashMap<String, String>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //listView((Item) dataSnapshot.getValue());
                for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                {
                    Item item =dataItem.getValue(Item.class);
                    itemInfoName=item.getItemName();
                    itemInfoShop=item.getShopName();
                    itemInfoDescription=item.getDesc();

                    String itemArr="Shop name: "+itemInfoShop+"\n"+"Description: "+itemInfoDescription+"\n";

                    if(itemInfoName.equalsIgnoreCase(getItemName))
                    {
                        itemURL=item.getURL();
                        adapter.notifyDataSetChanged();
                        arr.add(itemArr);
                        urlList.put(itemInfoShop, itemURL);
                        Log.e("item name",getItemName);
                        Log.e("url",itemURL);
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

        //ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                String selectedItem=listView.getItemAtPosition(position).toString();
                String[] parts = selectedItem.split("\n");
                String part1 = parts[0];
                String part2= parts[1];
                String[] partsList = part1.split(":");
                String shopName= partsList[1].trim();
                String itemDesc = parts[1];
                String itemUrl=urlList.get(shopName);
                //String itemURL =parts[2];


                i.putExtra("itemArr",selectedItem);
                i.putExtra("itemDesc", itemDesc);
                i.putExtra("shopName",shopName);
                i.putExtra("itemURL",itemURL);
                //Log.e("URL:",""+itemURL);

                startActivity(i);
            }
        });


    }


}