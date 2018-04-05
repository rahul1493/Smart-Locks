package com.example.user.smartlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

/**
 * Created by user on 19/3/18.
 */

public class CreateUser extends AppCompatActivity{

    Toolbar toolbar;
    Button button;
    private EditText username,password;
    private AwesomeValidation awesomeValidation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);


        AppHelper.init(getApplicationContext());
        AWSMobileClient.getInstance().initialize(this).execute();


        username= (EditText)findViewById(R.id.editText);
        password= (EditText) findViewById(R.id.editText2);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        //Awesome validation

        awesomeValidation.addValidation(this, R.id.editText, ".{6,}", R.string.usernameerror);
        awesomeValidation.addValidation(this, R.id.editText2, ".{6,}", R.string.passworderror);


        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button = (Button) findViewById(R.id.create);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (awesomeValidation.validate()) {

                CognitoUserAttributes userAttributes = new CognitoUserAttributes();

                userAttributes.addAttribute("email", "pratik.s.wagh95@gmail.com");

                AppHelper.getPool().signUpInBackground(username.getText().toString(), password.getText().toString(), userAttributes, null, signupCallback);

            }
            }
        });
    }


    SignUpHandler signupCallback = new SignUpHandler() {

        @Override
        public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            // Sign-up was successful

            Log.d("msg","msg_success");
            // Check if this user (cognitoUser) needs to be confirmed
            if(userConfirmed) {

                Log.d("msg","successful");
                Intent userActivity = new Intent(CreateUser.this, HomeActivity.class);
                //userActivity.putExtra("name", username);
                startActivityForResult(userActivity, 4);
            }
            else {

                confirmSignUp(cognitoUserCodeDeliveryDetails);
                Log.d("msg","msg1");

            }
        }

        @Override
        public void onFailure(Exception exception) {
            // Sign-up failed, check exception for the cause
            Log.d("msg",exception.getMessage());

        }
    };

    private void confirmSignUp(CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
        Intent intent = new Intent(this, SignUpConfirm.class);
        intent.putExtra("source","signup");
        intent.putExtra("name", username.getText().toString());
        intent.putExtra("destination", cognitoUserCodeDeliveryDetails.getDestination());
        intent.putExtra("deliveryMed", cognitoUserCodeDeliveryDetails.getDeliveryMedium());
        intent.putExtra("attribute", cognitoUserCodeDeliveryDetails.getAttributeName());
        startActivityForResult(intent, 10);
    }



}
