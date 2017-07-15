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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class itemDetails extends AppCompatActivity {
    private Button button;
    private Button buttonView;
    Intent i;
    Intent i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
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

        TextView tv = (TextView) findViewById(R.id.textViewShop);
        final String getItemArr = getIntent().getExtras().getString("itemArr");
        tv.setText(getItemArr);

        final String getShopName = getIntent().getExtras().getString("shopName");
        final String getItemName = getIntent().getExtras().getString("itemName");
        final String getURL = getIntent().getExtras().getString("itemURL");

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(getURL);

        i = new Intent(itemDetails.this, LoginActivity.class);
        //i.putExtra("zurag", images);
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
}


