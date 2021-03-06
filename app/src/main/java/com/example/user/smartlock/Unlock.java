package com.example.user.smartlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by user on 19/4/18.
 */

public class Unlock extends AppCompatActivity {


    AWSIotMqttManager mqttManager;
    String clientId;
    DynamoDBMapper dynamoDBMapper;
    Button unlock;
    Toolbar toolbar;
    CognitoCachingCredentialsProvider credentialsProvider;
    // Customer specific IoT endpoint
    // AWS Iot CLI describe-endpoint call returns: XXXXXXXXXX.iot.<region>.amazonaws.com,
    private static final String CUSTOMER_SPECIFIC_ENDPOINT = "a1kw7adkqhyy7v.iot.us-east-2.amazonaws.com";

    // Cognito pool ID. For this app, pool needs to be unauthenticated pool with
    // AWS IoT permissions.
    private static final String COGNITO_POOL_ID = "us-east-2:cbd064f2-8dfd-4bf6-89a6-341e4d22d699";

    // Region of AWS IoT
    private static final Regions MY_REGION = Regions.US_EAST_2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Instantiate a AmazonDynamoDBMapperClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        unlock=(Button) findViewById(R.id.unlock);

        clientId = UUID.randomUUID().toString();


        // Initialize the AWS Cognito credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(), // context
                COGNITO_POOL_ID, // Identity Pool ID
                MY_REGION // Region
        );

        // MQTT Client
        mqttManager = new AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_ENDPOINT);

        try {

            Log.d("msg", "Status = ");
            mqttManager.connect(credentialsProvider, new AWSIotMqttClientStatusCallback() {
                @Override
                public void onStatusChanged(final AWSIotMqttClientStatus status,
                                            final Throwable throwable) {
                    Log.d("Status", "Status = " + String.valueOf(status));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status == AWSIotMqttClientStatus.Connecting) {

                                Log.d("Status","Connecting..." );

                            }
                            else if (status == AWSIotMqttClientStatus.Connected) {

                                Log.d("Status","Connected" );



                            }
                            else if (status == AWSIotMqttClientStatus.Reconnecting) {
                                if (throwable != null) {
                                    Log.d("Status", "Connection error.", throwable);
                                }

                            }
                            else if (status == AWSIotMqttClientStatus.ConnectionLost) {
                                if (throwable != null) {
                                    Log.d("Status", "Connection error.", throwable);
                                    throwable.printStackTrace();
                                }

                            }
                        }
                    });
                }
            });
        } catch (final Exception e) {
            Log.e("error", "Connection error." + e.getMessage());

        }


        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        final String topic = "$aws/things/PubSub/shadow/update";
        final String msg = "{\"state\": {\"desired\": {\"message\": \"unlock\" }}}";

        try {
            mqttManager.publishString(msg, topic, AWSIotMqttQos.QOS0);





        } catch (Exception e) {
            Log.d("Status", "Publish error.", e);
        }
    }


});
    }
}
