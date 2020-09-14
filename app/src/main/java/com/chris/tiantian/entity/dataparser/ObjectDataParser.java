package com.chris.tiantian.entity.dataparser;

import android.text.TextUtils;
import android.util.Log;

import com.anima.networkrequest.data.okhttp.dataConvert.ResponseParser;
import com.google.gson.Gson;

/**
 * Created by jianjianhong on 19-11-25
 */
public class ObjectDataParser<T> implements ResponseParser {

    private T resultData;

    private String errorMessage;

   
    @Override
    public String errorMessage() {
        return errorMessage;
    }

    
    @Override
    public T getResult() {
        return resultData;
    }

    @Override
    public int getTotal() {
        return 1;
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
                resultData = (T) new Gson().fromJson(s.toString(), aClass);
            } catch (Exception e) {
                errorMessage = e.getMessage();
            }
        }

        return this;
    }
}
