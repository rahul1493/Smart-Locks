package com.example.user.smartlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;


/**
 * Created by user on 19/4/18.
 */

public class ChangePassword extends AppCompatActivity {

    String Username;
    private EditText oldpassord,newpassword;
    Toolbar toolbar;
    Button change;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        oldpassord = (EditText) findViewById(R.id.oldpassword);
        newpassword = (EditText) findViewById(R.id.newpassword);
        final CognitoUser user = AppHelper.getPool().getCurrentUser();

        change=(Button) findViewById(R.id.change);




        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                user.changePasswordInBackground(String.valueOf(oldpassord), String.valueOf(newpassword), handler);

            }
        });

    }


    GenericHandler handler = new GenericHandler() {

        @Override
        public void onSuccess() {
           Log.d("msg","s");
            Intent userActivity = new Intent(ChangePassword.this, HomeActivity.class);
            //userActivity.putExtra("name", username);
            startActivityForResult(userActivity, 4);
        }

        @Override
        public void onFailure(Exception exception) {
            // Password change failed, probe exception for details
            Log.d("msg","f");
            Log.d("msg",exception.getMessage());
        }
    };

}
