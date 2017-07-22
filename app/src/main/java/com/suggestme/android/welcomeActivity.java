package com.suggestme.android;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class welcomeActivity extends AppCompatActivity {
    //private EditText editTextInput;
    public SearchView searchbar;
    public ListView listView;
    public ArrayAdapter adapter;
    public Button button;
    public Intent intent;
    String itemName;
    public Item item;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_welcome);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("items");
        searchbar=(SearchView) findViewById(R.id.searchbar1);
        //button=(Button) findViewById(R.id.button2);

        listView=(ListView)findViewById(R.id.listview);
        final List<String> mDataList=new ArrayList<>();


        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Query searchQuery=mDatabase.orderByChild("itemName")
                       .startAt(searchbar.getQuery().toString().trim())//searchbar.getQuery().toString().trim().indexOf(0)
                       .endAt(searchbar.getQuery().toString().trim()+"\uf8ff");//
                //.equalTo(searchbar.getQuery().toString().trim());

                searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot dataItem: dataSnapshot.getChildren() )
                            {
                                item =dataItem.getValue(Item.class);
                                itemName=item.getItemName();

                                adapter.notifyDataSetChanged();
                                mDataList.add(itemName);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                searchbar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDataList.clear();
                return true;
            }
        });

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mDataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                String selectedItem=listView.getItemAtPosition(position).toString();
                Intent i = new Intent(welcomeActivity.this, searchResult.class);
                i.putExtra("itemName", selectedItem);
                Log.e("jump to the next page", selectedItem);

                startActivity(i);
            }
        });


        Button loginBtn=(Button) findViewById(R.id.loginBtu);
        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
                            startActivity(new Intent(welcomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                };

                Intent intent1 = new Intent(welcomeActivity.this, LoginActivity.class);
                //intent1.putExtra("username",edUsername);//edUsername.getText().toString()
                signOut();
                startActivity(intent1);
                //startActivity(new Intent(welcomeActivity.this, searchResult.class));
            }
        });


    }
    public void signOut() {
        auth.signOut();
    }

}
