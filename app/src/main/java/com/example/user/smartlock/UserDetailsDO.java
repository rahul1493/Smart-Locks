package com.example.user.smartlock;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "smartlock-mobilehub-1560124461-user_details")

public class UserDetailsDO {
    private String _username;
    private String _emailid;
    private String _timeofcreation;

    @DynamoDBHashKey(attributeName = "username")
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return _username;
    }

    public void setUsername(final String _username) {
        this._username = _username;
    }
    @DynamoDBAttribute(attributeName = "emailid")
    public String getEmailid() {
        return _emailid;
    }

    public void setEmailid(final String _emailid) {
        this._emailid = _emailid;
    }
    @DynamoDBAttribute(attributeName = "timeofcreation")
    public String getTimeofcreation() {
        return _timeofcreation;
    }

    public void setTimeofcreation(final String _timeofcreation) {
        this._timeofcreation = _timeofcreation;
    }

}
