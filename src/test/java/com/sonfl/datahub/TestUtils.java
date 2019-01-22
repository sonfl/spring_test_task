package com.sonfl.datahub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sonfl.datahub.api.model.ProductListDTO;

import java.util.List;

public class TestUtils {
    @SuppressWarnings("rawtypes")
    public static ProductListDTO jsonToList(String json, TypeToken token) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(json, token.getType());
    }

    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> classOf) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(json, classOf);
    }
}
