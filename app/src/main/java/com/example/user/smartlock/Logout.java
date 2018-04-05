package com.example.user.smartlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;

/**
 * Created by user on 19/3/18.
 */

public class Logout extends AppCompatActivity {

    private String Username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CognitoUser user = AppHelper.getPool().getCurrentUser();

        user.signOut();

        Intent c = new Intent(Logout.this, MainActivity.class);
        startActivity(c);
    }
}
