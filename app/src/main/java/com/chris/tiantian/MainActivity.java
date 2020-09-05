package com.chris.tiantian;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.module.main.activity.TiantianFragment;
import com.chris.tiantian.module.me.activity.MeFragment;
import com.chris.tiantian.module.message.MessageFragment;
import com.chris.tiantian.module.strategy.StrategyFragment;
import com.chris.tiantian.util.BackgrounderSetting;
import com.chris.tiantian.util.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

import static com.chris.tiantian.entity.Constant.APP_ID;


public class MainActivity extends AppCompatActivity {

    private View toolbarLayout;
    private Toolbar toolbar;
    private BottomNavigationView navigationView;
    private int currentPageIndex = 0;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int i = 1/0;
        if (savedInstanceState!=null){
            currentPageIndex = savedInstanceState.getInt("KEY_PAGE_INDEX");
        }else {
            currentPageIndex = getIntent().getIntExtra("KEY_PAGE_INDEX", currentPageIndex);
        }

        toolbarLayout = findViewById(R.id.activity_toolBar_layout);
        toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setShowbackbtn(false);
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
        fragments.add(new TiantianFragment());
        fragments.add(new StrategyFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MeFragment());
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        switchFragment(0);
                        toolbarLayout.setVisibility(View.GONE);
                        break;
                    case R.id.bottom_nav_role:
                        switchFragment(1);
                        toolbar.setTitle((String) item.getTitle());
                        toolbarLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_nav_message:
                        switchFragment(2);
                        toolbar.setTitle((String)item.getTitle());
                        toolbarLayout.setVisibility(View.VISIBLE);

                        break;
                    case R.id.bottom_nav_me:
                        switchFragment(3);
                        toolbar.setTitle((String) item.getTitle());
                        toolbarLayout.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

        setCurrentPage();
    }

    private void switchFragment(int pos) {
        //Toast.makeText(this,prePos+" -> "+pos,Toast.LENGTH_LONG).show();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment from = fragments.get(currentPageIndex);
        Fragment to = fragments.get(pos);
        if(!to.isAdded()){
            if(from != to) {
                transaction.hide(from);
            }

            transaction.add(R.id.content_frame,fragments.get(pos), pos+"")
                    .commit();
        }else{
            transaction.hide(from)
                    .show(to)
                    .commit();
        }
        currentPageIndex = pos;
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
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }
}
