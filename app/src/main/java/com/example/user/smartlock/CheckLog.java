package com.example.user.smartlock;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.regions.Regions;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 19/3/18.
 */

public class CheckLog extends AppCompatActivity {


    Toolbar toolbar;
    DynamoDBMapper dynamoDBMapper;
    AmazonDynamoDBClient dynamoDBClient;
    private RecyclerView recyclerView;
    private LogAdapter logAdapter;
    private List<LogsDO> result = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);








        // Instantiate a AmazonDynamoDBMapperClient
        dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            logs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SystemClock.sleep(3000);
        logAdapter=new LogAdapter(this,result);
        recyclerView.setAdapter(logAdapter);

        Log.d("msg","reached");
    }

    public void logs() throws Exception {


        new Thread(new Runnable() {
            @Override
            public void run() {

                DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);


                System.out.println("Scanning Tesis");


                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

                System.out.println("Scanning Tesis");


                result = mapper.scan(LogsDO.class, scanExpression);
                System.out.println("Scanning Tesis");
                System.out.println(result.toString());
                Log.d("msg",result.toString());

                for (LogsDO log : result) {
                    Log.d("user", String.valueOf(log));
                    System.out.println(log.getUserId());

                    System.out.println(log.getTimestamp());

                }



            }
        }).start();




    }
}
