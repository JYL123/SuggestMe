package com.suggestme.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.URL;

public class itemDetails extends AppCompatActivity {
    private Button button;
    private Button buttonView;
    Intent i;
    Intent i2;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = (TextView) findViewById(R.id.textViewShop);
        final String getItemArr = getIntent().getExtras().getString("itemArr");
        tv.setText(getItemArr);

        final String getShopName = getIntent().getExtras().getString("shopName");
        final String getItemName = getIntent().getExtras().getString("itemName");
        final String getURL = getIntent().getExtras().getString("itemURL");
        auth = FirebaseAuth.getInstance();

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(getURL);

        i = new Intent(itemDetails.this, LoginActivity.class);
        i.putExtra("itemArr", getItemArr);
        i.putExtra("shopName", getShopName);
        i.putExtra("itemName", getItemName);

        button = (Button) findViewById(R.id.buttonComment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                finish();
            }
        });

        i2=new Intent(itemDetails.this, ViewComments.class);
        i2.putExtra("itemName", getItemName);
        i2.putExtra("shopName", getShopName);
        Log.e("item name",getItemName);
        Log.e("shop name",getShopName);

        buttonView = (Button) findViewById(R.id.buttonView);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i2);
                finish();
            }
        });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
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
                //startActivity(new Intent(this, searchResult.class));
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


