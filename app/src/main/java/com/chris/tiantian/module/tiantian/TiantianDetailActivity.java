package com.chris.tiantian.module.tiantian;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.New;
import com.ut.utuicomponents.uttoolbar.UTUiAndroidToolbar;

/**
 * Created by jianjianhong on 19-12-23
 */
public class TiantianDetailActivity extends AppCompatActivity {
    public static final String NEW_DATA = "new_data";

    private New newData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiantian_detail);

        newData = getIntent().getParcelableExtra(NEW_DATA);
        UTUiAndroidToolbar toolbar = findViewById(R.id.activity_toolBar);
        TextView titleView = findViewById(R.id.detailAct_new_title);
        TextView comeFromView = findViewById(R.id.detailAct_new_comeForm);
        TextView timeView = findViewById(R.id.detailAct_new_time);
        TextView textView = findViewById(R.id.detailAct_new_text);

        toolbar.setTitle(newData.getTitle());
        titleView.setText(newData.getTitle());
        comeFromView.setText(newData.getComefrom());
        timeView.setText(newData.getTime());
        textView.setText(newData.getText());
    }
}
