package com.chris.tiantian;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.module.me.activity.MeFragment;
import com.chris.tiantian.module.plaza.activity.PlazaFragment;
import com.chris.tiantian.module.signal.activity.PolicySignalFragment;
import com.chris.tiantian.module.main.activity.TiantianFragment;
import com.chris.tiantian.util.BackgrounderSetting;
import com.chris.tiantian.util.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import rx.functions.Action1;

import static com.chris.tiantian.entity.Constant.WX_APP_ID;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
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
        }else {
            currentPageIndex = getIntent().getIntExtra("KEY_PAGE_INDEX", currentPageIndex);
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
                                //VersionUploadService.checkUpdateInBackground(MainActivity.this);
                                BackgrounderSetting.open(MainActivity.this);
                            }
                        }
                    });
        }else {
            BackgrounderSetting.open(this);
        }

        regToWx();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int newPageIndex = intent.getIntExtra("KEY_PAGE_INDEX", currentPageIndex);
        if(newPageIndex != currentPageIndex) {
            currentPageIndex = newPageIndex;
            setCurrentPage();
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
                            plazaFragment = new PlazaFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, plazaFragment).commitAllowingStateLoss();
                        toolbar.setTitle((String)item.getTitle());
                        toolbar.setVisibility(View.GONE);
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

        setCurrentPage();
    }

    private void setCurrentPage() {
        navigationView.setSelectedItemId(navigationView.getMenu().getItem(currentPageIndex).getItemId());
        toolbar.setTitle((String) navigationView.getMenu().getItem(currentPageIndex).getTitle());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("KEY_PAGE_INDEX", currentPageIndex);
    }

    private void regToWx() {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        iwxapi.registerApp(WX_APP_ID);
    }
}
