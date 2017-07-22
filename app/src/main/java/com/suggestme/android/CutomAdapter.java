package com.suggestme.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by ljy on 1/7/17.
` */

public class CutomAdapter extends ArrayAdapter<String>{


    public CutomAdapter(@NonNull Context context, String[] foods) {
        super(context, R.layout.activity_test,foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater buckyInflater = LayoutInflater.from(getContext());
        View customView=buckyInflater.inflate(R.layout.activity_test,parent, false);

        String singleItem=getItem(position);
        TextView buckyText=(TextView) customView.findViewById(R.id.text1);
        ImageView buckyImage = (ImageView) customView.findViewById(R.id.image1);

        buckyText.setText(singleItem);
        buckyImage.setImageResource(R.drawable.applogo);
        return customView;
    }
}
