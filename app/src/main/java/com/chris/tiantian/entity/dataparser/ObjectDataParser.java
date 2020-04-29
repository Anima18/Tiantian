package com.chris.tiantian.entity.dataparser;

import android.text.TextUtils;

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
        try {
            //Type type = new ParameterizedTypeImpl(List.class, new Class[]{aClass});
            resultData = (T)new Gson().fromJson(s, aClass);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        return this;
    }
}
