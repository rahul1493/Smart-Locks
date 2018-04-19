package com.example.user.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChooseMfaContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.NewPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    Button button;
    private String Username,Password;
    private EditText username,password;
    private MultiFactorAuthenticationContinuation Continuation;
    private AwesomeValidation awesomeValidation;
    boolean doubleBackToExitPressedOnce = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //AppHelper.init(getApplicationContext());
        AWSMobileClient.getInstance().initialize(this).execute();


        username= (EditText)findViewById(R.id.editText);
        password= (EditText) findViewById(R.id.editText2);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        AppHelper.init(getApplicationContext());
        CognitoUser user = AppHelper.getPool().getCurrentUser();

        Log.d("msg", user.toString());

        Username = user.getUserId();
        if(Username != null) {
            //AppHelper.setUser(username);
            //inUsername.setText(user.getUserId());
            user.getSessionInBackground(authenticationHandler);
        }



        //Awesome validation

        awesomeValidation.addValidation(this, R.id.editText, ".{1,}", R.string.usernameerror);
        awesomeValidation.addValidation(this, R.id.editText2, ".{6,}", R.string.passworderror);


        button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Username = username.getText().toString();

                if (awesomeValidation.validate()) {

                AppHelper.getPool().getUser(Username).getSessionInBackground(authenticationHandler);
               }

            }
        });
    }

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {

            AppHelper.setCurrSession(cognitoUserSession);
            AppHelper.newDevice(device);

            Log.d("msg", "success");
            Log.d("msg", Username);

            if (Objects.equals(Username, "embedded")) {
                Intent userActivity = new Intent(MainActivity.this, HomeActivity.class);
                //userActivity.putExtra("name", username);
                startActivityForResult(userActivity, 4);
            }
            else
            {
                Intent userActivity = new Intent(MainActivity.this, HomeActivityOther.class);
                //userActivity.putExtra("name", username);
                startActivityForResult(userActivity, 4);
            }
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String username) {

            if(username != null) {
                Username = username;

            }

            // The API needs user sign-in credentials to continue
            Password=password.getText().toString();

            AuthenticationDetails authenticationDetails = new AuthenticationDetails(Username, Password, null);

            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);

            // Allow the sign-in to continue
            authenticationContinuation.continueTask();

        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {

            // Multi-factor authentication is required; get the verification code from user
           // multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
            // Allow the sign-in process to continue
            Log.d("msg","mfa");
            multiFactorAuthenticationContinuation.continueTask();
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

            Log.d("msg","challenge");
        }

        @Override
        public void onFailure(Exception e) {
            Log.d("msg","exception");
            Log.d("msg",e.getMessage());

        }

    };

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
