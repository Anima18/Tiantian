package com.chris.tiantian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.chris.tiantian.module.main.PolicyMonitorService;
import com.chris.tiantian.module.main.PolicyMonitorService2;
import com.chris.tiantian.module.main.activity.PolicySignalFragment2;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.chris.tiantian.module.main.activity.MeFragment;
import com.chris.tiantian.module.main.activity.PolicyFragment;
import com.chris.tiantian.module.main.activity.TiantianFragment;
import com.chris.tiantian.util.BottomNavigationViewHelper;
import com.ut.utuicomponents.uttoolbar.UTUiAndroidToolbar;

public class MainActivity extends AppCompatActivity {

    private UTUiAndroidToolbar toolbar;
    private BottomNavigationView navigationView;
    private Fragment tiantainFragment;
    private Fragment subscriptionFragment;
    private Fragment plazaFragment;
    private Fragment meFragment;

    //private Intent policyMonitoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.activity_toolBar);
        navigationView = findViewById(R.id.bottom_nav);
        initNavigationView();

        /*policyMonitoIntent = new Intent(this, PolicyMonitorService.class);
        startService(policyMonitoIntent);*/
    }

    private void initNavigationView() {
        BottomNavigationViewHelper.disableShiftMode(navigationView);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        if(tiantainFragment == null) {
                            tiantainFragment = new TiantianFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, tiantainFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String) item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_nav_role:
                        if(subscriptionFragment == null) {
                            subscriptionFragment = new PolicySignalFragment2();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, subscriptionFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String) item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_nav_message:
                        if(plazaFragment == null) {
                            plazaFragment = new PolicyFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, plazaFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String)item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_nav_me:
                        if(meFragment == null) {
                            meFragment = new MeFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, meFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String) item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

        navigationView.setSelectedItemId(navigationView.getMenu().getItem(0).getItemId());
        toolbar.setTitle((String) navigationView.getMenu().getItem(0).getTitle());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stopService(policyMonitoIntent);
    }
}
