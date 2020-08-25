package com.cyp.robot.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * A usage tool for json convert.
 *
 * @author chengyao.liang
 */
public class ObjectMapperUtils {

    public final static ObjectMapper mapper = new ObjectMapper();

    static {
        // if you need to change default configuration:
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // don't see any dynamic type usage as of now, set this to true
        // performance gain
        // persistence.configure(SerializationConfig.Feature.USE_STATIC_TYPING,
        // true);
        // will not print null property any more~~
        //persistence.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        T result = null;
        try {
            result = mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T fromJson(String json, TypeReference<T> clazz) {
        T result = null;
        try {
            result = mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String toJson(Object createRequest) {
        String result = null;
        try {
            result = mapper.writeValueAsString(createRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String toJsonNotDefault(Object createRequest) {
        String result = null;
        try {
            mapper.setSerializationInclusion(Include.NON_DEFAULT);
            result = mapper.writeValueAsString(createRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
