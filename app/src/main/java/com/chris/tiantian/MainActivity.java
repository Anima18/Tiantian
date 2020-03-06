package com.chris.tiantian;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.chris.tiantian.base.service.VersionUploadService;
import com.chris.tiantian.module.main.activity.MeFragment;
import com.chris.tiantian.module.main.activity.PolicyFragment;
import com.chris.tiantian.module.main.activity.PolicySignalFragment;
import com.chris.tiantian.module.main.activity.TiantianFragment;
import com.chris.tiantian.util.BottomNavigationViewHelper;
import com.chris.tiantian.util.AutoStartupSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ut.utuicomponents.uttoolbar.UTUiAndroidToolbar;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private UTUiAndroidToolbar toolbar;
    private BottomNavigationView navigationView;
    private Fragment tiantainFragment;
    private Fragment subscriptionFragment;
    private Fragment plazaFragment;
    private Fragment meFragment;
    private int currentPageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            currentPageIndex = savedInstanceState.getInt("KEY_PAGE_INDEX");
        }

        toolbar = findViewById(R.id.activity_toolBar);
        navigationView = findViewById(R.id.bottom_nav);
        initNavigationView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                VersionUploadService.checkUpdateInBackground(MainActivity.this);
                                AutoStartupSetting.open(MainActivity.this);
                            }
                        }
                    });
        }else {
            AutoStartupSetting.open(this);
        }


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
                        currentPageIndex = 0;
                        break;
                    case R.id.bottom_nav_role:
                        if(subscriptionFragment == null) {
                            subscriptionFragment = new PolicySignalFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, subscriptionFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String) item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        currentPageIndex = 1;
                        break;
                    case R.id.bottom_nav_message:
                        if(plazaFragment == null) {
                            plazaFragment = new PolicyFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, plazaFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String)item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        currentPageIndex = 2;
                        break;
                    case R.id.bottom_nav_me:
                        if(meFragment == null) {
                            meFragment = new MeFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, meFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String) item.getTitle());
                        toolbar.setVisibility(View.VISIBLE);
                        currentPageIndex = 3;
                        break;
                }
                return true;
            }
        });

        navigationView.setSelectedItemId(navigationView.getMenu().getItem(currentPageIndex).getItemId());
        toolbar.setTitle((String) navigationView.getMenu().getItem(currentPageIndex).getTitle());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("KEY_PAGE_INDEX", currentPageIndex);
    }
}
