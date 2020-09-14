package com.chris.tiantian.entity.dataparser;

import android.text.TextUtils;

import com.anima.networkrequest.data.okhttp.dataConvert.ResponseParser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jianjianhong on 19-11-25
 */
public class StringDataParser implements ResponseParser {

    private String resultData;

    private String errorMessage;

    @NotNull
    @Override
    public String errorMessage() {
        return errorMessage;
    }

    @Nullable
    @Override
    public String getResult() {
        return resultData;
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public boolean isSuccess() {
        return TextUtils.isEmpty(errorMessage);
    }
   
    @Override
    public ResponseParser parser(String s, Class<?> aClass) {
        if(TextUtils.isEmpty(s)) {
            errorMessage = "没有数据";
        }else {
            try {
                resultData = s;
            } catch (Exception e) {
                errorMessage = e.getMessage();
            }
        }

        return this;
    }
}
