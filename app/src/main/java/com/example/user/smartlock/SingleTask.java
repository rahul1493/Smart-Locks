package com.example.user.smartlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by user on 16/4/18.
 */

public class SingleTask extends AppCompatActivity{


    private String username,dateofcreation;
    private TextView Username,TimeOfCreation;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleuser);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = getIntent().getExtras().getString("Username");
        dateofcreation = getIntent().getExtras().getString("TimeofCreation");

        Log.d("msg1",username);
        Log.d("msg2",dateofcreation);

        Username = (TextView)findViewById(R.id.username);
        TimeOfCreation = (TextView)findViewById(R.id.timeofcreation);

        Username.setText(username);
        TimeOfCreation.setText(dateofcreation);


    }
}
