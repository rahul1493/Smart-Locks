package com.example.user.smartlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.Map;

/**
 * Created by user on 19/3/18.
 */

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout Drawer;
    private ActionBarDrawerToggle Toggle;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        Drawer=(DrawerLayout) findViewById(R.id.drawerlayout);
        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.checklog:
                                Intent a = new Intent(HomeActivity.this, CheckLog.class);
                                startActivity(a);
                                break;
                            case R.id.createuser:
                                Intent b = new Intent(HomeActivity.this, CreateUser.class);
                                startActivity(b);
                                break;
                            case R.id.allusers:
                                Intent c = new Intent(HomeActivity.this, AllUsers.class);
                                startActivity(c);
                                break;
                            case R.id.generatepattern:
                                Intent d = new Intent(HomeActivity.this, GeneratePattern.class);
                                startActivity(d);
                                break;
                            case R.id.notification:
                                Intent e = new Intent(HomeActivity.this, Notification.class);
                                startActivity(e);
                                break;

                            case R.id.logout:
                                Intent f = new Intent(HomeActivity.this, Logout.class);
                                startActivity(f);
                                break;
                        }

                        return true;
                    }
                });

        Toggle=new ActionBarDrawerToggle(this,Drawer,R.string.open,R.string.close);

        Drawer.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        if(Toggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
