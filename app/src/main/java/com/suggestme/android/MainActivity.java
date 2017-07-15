package com.suggestme.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button commentBtn, rateBtn, userBtn;
    private TextView appName, txtEmail;
    private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View navHeader;
    private TextView textView1;
    private TextView textView2;
    private ArrayAdapter adapter;
    private Intent myIntent;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private Intent intent1;
    private Intent intent2;
    private Intent intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("items");

        final String getItemArr=getIntent().getExtras().getString("itemArr");
        final String getShopName=getIntent().getExtras().getString("shopName");
        final String getItemName=getIntent().getExtras().getString("itemName");
        final String getEmail=getIntent().getExtras().getString("getEmail");

        textView1.setText(getItemName);
        textView2.setText(getItemArr);

        intent1 = new Intent(new Intent(MainActivity.this, CommentActivity.class));
        intent2 = new Intent(new Intent(MainActivity.this, RateActivity.class));
        intent3 = new Intent(new Intent(MainActivity.this, changeCredentials.class));
        //i.putExtra("zurag", images);
        intent1.putExtra("itemArr", getItemArr);
        intent1.putExtra("shopName", getShopName);
        intent1.putExtra("itemName", getItemName);
        intent2.putExtra("itemArr", getItemArr);
        intent2.putExtra("shopName", getShopName);
        intent2.putExtra("itemName", getItemName);
        intent3.putExtra("userEmail", getEmail);
        commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
              // startActivity(new Intent(MainActivity.this, CommentActivity.class));
                startActivity(intent1);
                finish();
           }

        });

        rateBtn = (Button) findViewById(R.id.rateBtn);
        rateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               // startActivity(new Intent(MainActivity.this, RateActivity.class));
                startActivity(intent2);
                finish();
            }

        });

        userBtn = (Button) findViewById(R.id.changeUserNameButton);
        userBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // startActivity(new Intent(MainActivity.this, RateActivity.class))
                startActivity(intent3);
                finish();
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
            case R.id.settings:
                startActivity(new Intent(this, UserSettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
