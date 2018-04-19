package com.example.user.smartlock;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by user on 2/4/18.
 */

public class SignUpConfirm extends AppCompatActivity {

    private String userName,emailid;
    private EditText username;
    private EditText confCode;
    Button confirm;
    DynamoDBMapper dynamoDBMapper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_confirm);


        // Instantiate a AmazonDynamoDBMapperClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        init();
    }

    private void init()
    {
        String confirmCode = null;
        Bundle extras = getIntent().getExtras();
        if (extras !=null) {

            userName = extras.getString("name");

            emailid = extras.getString("email");
            username = (EditText) findViewById(R.id.editText3);
            username.setText(userName);

            confCode = (EditText) findViewById(R.id.editText4);
            confCode.requestFocus();

            confirm= (Button) findViewById(R.id.confirm);
        }


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = username.getText().toString();
                String confirmCode = confCode.getText().toString();
                AppHelper.getPool().getUser(userName).confirmSignUpInBackground(confirmCode, true, confHandler);
            }
        });

    }

    GenericHandler confHandler = new GenericHandler() {
        @Override
        public void onSuccess() {


            createUser();

            Intent userActivity = new Intent(SignUpConfirm.this, HomeActivity.class);
            //userActivity.putExtra("name", username);
            startActivityForResult(userActivity, 4);

        }

        @Override
        public void onFailure(Exception exception) {

        }
    };

    public void createUser() {
        final UserDetailsDO userdata = new UserDetailsDO();

        userdata.setUsername(userName);

        userdata.setEmailid(emailid);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        userdata.setTimeofcreation(currentDateTimeString);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userdata);
                // Item saved
            }
        }).start();
    }

}
