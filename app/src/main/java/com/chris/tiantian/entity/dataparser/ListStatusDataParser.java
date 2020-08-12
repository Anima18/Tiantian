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
public class ListStatusDataParser<T> implements ResponseParser {

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
            Type type = new ParameterizedTypeImpl(ListStatusData.class, new Class[]{aClass});
            ListStatusData statusData = (ListStatusData<T>)new Gson().fromJson(new StringReader(s), type);;
            if(statusData.getCode() == 0) {
                resultData = (List<T>)statusData.getData();
            }else {
                errorMessage = statusData.getMsg();
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        return this;
    }
}
