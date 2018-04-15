package com.example.user.smartlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by user on 19/3/18.
 */

public class GeneratePattern  extends AppCompatActivity {


    Toolbar toolbar;
    private Button generate;
    private TextView pattern;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatepattern);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        generate = (Button) findViewById(R.id.generatepattern);
        pattern = (TextView) findViewById(R.id.pattern);



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random r = new Random();
                int i1 = r.nextInt(5 - 0) + 0;
                int i2 = r.nextInt(5 - 0) + 0;
                int i3 = r.nextInt(5 - 0) + 0;
                int i4 = r.nextInt(5 - 0) + 0;
                int i5 = r.nextInt(5 - 0) + 0;

                String finalpattern = i1 +"," + i2 +"," + i3 + "," + i4 + "," +i5;
                pattern.setText(finalpattern);

            }
        });
    }
}
