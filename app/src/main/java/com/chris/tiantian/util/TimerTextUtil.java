package com.chris.tiantian.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by jianjianhong on 20-3-27
 */
public class TimerTextUtil extends CountDownTimer {
    private TextView textView;
    private boolean finished = true;
    public TimerTextUtil(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time =  millisUntilFinished / 1000 + "秒后...";
        setTextInfo(time, "#727272", false);
        finished = false;
    }

    @Override
    public void onFinish() {
        setTextInfo("获取验证码", "#1b82d2", true);
        finished = true;
    }

    private void setTextInfo(String content, String color, boolean isClick) {
        textView.setText(content);
        textView.setTextColor(Color.parseColor(color));
        textView.setClickable(isClick);
    }

    public boolean isFinished() {
        return finished;
    }
}
