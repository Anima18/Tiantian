package com.chris.tiantian.util.jsonParse;

import java.io.Reader;
import java.lang.reflect.Type;


/**
 * Created by jianjianhong on 18-8-31
 */
public interface UTJson {
    <T> T fromJson(String json, Type typeOfT);

    <T> T fromJson(String json, Class<T> classOfT);

    <T> T fromJson(Reader json, Type typeOfT);

    String toJson(Object data);

}
