package com.chris.tiantian.entity;

import android.text.TextUtils;

import com.anima.networkrequest.data.okhttp.dataConvert.ParameterizedTypeImpl;
import com.anima.networkrequest.data.okhttp.dataConvert.ResponseParser;
import com.google.gson.Gson;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jianjianhong on 19-11-25
 */
public class NetworkDataParser<T> implements ResponseParser {

    private List<T> resultData;

    private String errorMessage;

   
    @Override
    public String errorMessage() {
        return errorMessage;
    }

    
    @Override
    public List<T> getResult() {
        return resultData;
    }

    @Override
    public int getTotal() {
        return resultData != null ? resultData.size() : 0;
    }

    @Override
    public boolean isSuccess() {
        return TextUtils.isEmpty(errorMessage);
    }

   
    @Override
    public ResponseParser parser(String s, Class<?> aClass) {
        try {
            Type type = new ParameterizedTypeImpl(List.class, new Class[]{aClass});
            resultData = new Gson().fromJson(new StringReader(s), type);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        return this;
    }
}
