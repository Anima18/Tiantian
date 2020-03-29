package com.chris.tiantian.module.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.tiantian.R;
import com.chris.tiantian.util.TimerTextUtil;

/**
 * Created by jianjianhong on 20-3-26
 */
public class SMSverificationActivity extends AppCompatActivity {

    private TextView timerTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        timerTextView = findViewById(R.id.timeTv);

        TimerTextUtil timerTextUtil = new TimerTextUtil(60000L, 1000L, timerTextView);
        timerTextUtil.start();

        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTextUtil.start();
            }
        });
    }
}
