package com.example.user.smartlock;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;


/**
 * Created by user on 31/3/18.
 */

public class AppHelper {
    private static CognitoUserPool userPool;


    private static CognitoDevice thisDevice;
    private static CognitoUserSession currSession;


    public static void init(Context context) {

        if (userPool == null) {

            ClientConfiguration clientConfiguration = new ClientConfiguration();


            // Create a user pool with default ClientConfiguration
            userPool = new CognitoUserPool(context, "us-east-2_l4mIhcfgn", "6c5ih5r9g3rh14bt6paaflfsn0", "2p6en9fv7poh22eakdhp7v2963brcn64rdvouqh7qo8nfpaucje",Regions.US_EAST_2);


        }

    }
    private static CognitoDevice newDevice;

    public static void newDevice(CognitoDevice device) {
        newDevice = device;
    }

    public static void setThisDevice(CognitoDevice device) {
        thisDevice = device;
    }

    public static void setCurrSession(CognitoUserSession session) {
        currSession = session;
    }

    public static CognitoUserPool getPool() {
        return userPool;
    }
}
