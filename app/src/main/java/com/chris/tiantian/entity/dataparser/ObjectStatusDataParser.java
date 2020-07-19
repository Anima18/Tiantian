package com.chris.tiantian.entity.dataparser;

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
public class ObjectStatusDataParser<T> implements ResponseParser {

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
            Type type = new ParameterizedTypeImpl(StatusData.class, new Class[]{aClass});
            StatusData statusData = new Gson().fromJson(new StringReader(s), type);;
            if(statusData.getCode() == 0) {
                resultData = (T)statusData.getData();
            }else {
                errorMessage = statusData.getMsg();
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        return this;
    }
}
