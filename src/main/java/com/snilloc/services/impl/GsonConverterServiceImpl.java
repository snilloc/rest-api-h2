package com.snilloc.services.impl;

import com.google.gson.Gson;
import com.snilloc.exceptions.JsonConversionException;

import java.lang.reflect.Type;

public class GsonConverterServiceImpl {
    private final Gson gson;

    public GsonConverterServiceImpl() {
        gson = new Gson();
    }

    public String toJson(Object document) throws JsonConversionException {
            try {
                return gson.toJson(document);
            } catch (Exception ex) {
                throw new JsonConversionException("Failed to convert object to json.", ex);

        }
    }

    public Object fromJson(String jsonString, Type object)  throws JsonConversionException {
        try {
            Object result = gson.fromJson(jsonString, object);
            return result;
        } catch (Exception ex) {
            throw new JsonConversionException("Failed to convert to Object: " + object.getTypeName(), ex);

        }
    }
}
