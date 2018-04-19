package com.example.user.smartlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.common.collect.Table;


import java.text.DateFormat;
import java.util.Date;

/**
 * Created by user on 16/4/18.
 */

public class SingleTask extends AppCompatActivity{


    private String username,dateofcreation;
    private TextView Username,TimeOfCreation;
    Toolbar toolbar;
    AmazonDynamoDBClient dynamoDBClient;
    private Button remove;
    DynamoDBMapper dynamoDBMapper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleuser);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        // Instantiate a AmazonDynamoDBMapperClient
        dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        username = getIntent().getExtras().getString("Username");
        dateofcreation = getIntent().getExtras().getString("TimeofCreation");

        Log.d("msg1",username);
        Log.d("msg2",dateofcreation);

        Username = (TextView)findViewById(R.id.username);
        TimeOfCreation = (TextView)findViewById(R.id.timeofcreation);
        remove=(Button) findViewById(R.id.delete);

        Username.setText(username);
        TimeOfCreation.setText(dateofcreation);

        final CognitoUser user = AppHelper.getPool().getCurrentUser();



    }

    GenericHandler handler = new GenericHandler() {

        @Override
        public void onSuccess() {

           // deleteUser();

            Intent userActivity = new Intent(SingleTask.this, AllUsers.class);
            //userActivity.putExtra("name", username);
            startActivityForResult(userActivity, 4);

        }

        public void onFailure(Exception exception) {
            Log.d("msg","exception");
            Log.d("msg",String.valueOf(exception.getMessage()));

        }
    };

    public void deleteUser() {
        final UserDetailsDO userdata = new UserDetailsDO();

       // AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());

       /* userdata.setUsername(userName);

        userdata.setEmailid("pratik.s.wagh95@gmail.com");

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        userdata.setTimeofcreation(currentDateTimeString);*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {

                DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
                        new ProfileCredentialsProvider()));

                AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                        .build();

                DynamoDB dynamoDB = new DynamoDB(client);

                Table table = dynamoDB.getTable("Movies");

                AmazonDynamoDB client = AmazonDynamoDBAsyncClientBuilder.standard().build();

                DynamoDB dynamoDB = new DynamoDB(client);

                Table table = dynamoDB.getTable("userdetails");

                CognitoUser user = AppHelper.getPool().getCurrentUser();

                DeleteItemOutcome outcome = table.deleteItem("username",user.getUserId());
               // dynamoDBMapper.save(userdata);
                // Item saved
            }
        }).start();*/
    }

}
