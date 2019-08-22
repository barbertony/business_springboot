package com.neuedu.utils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtils {

    private  static ObjectMapper objectMapper=new ObjectMapper();
    static {
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);


    }
    public  static <T> String obj2String(T obj){
        if (obj==null)
        {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj: objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static <T> String obj2StringPretty(T obj){
        if (obj==null)
        {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T string2Obj(String str,Class<T> clazz)
    {
        if (StringUtils.isNotEmpty(str)||clazz==null)
        {
            return null;
        }
        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T string2Obj(String str, TypeReference<T> tTypeReference)
    {
        if (StringUtils.isNotEmpty(str)||tTypeReference==null)
        {
            return null;
        }
        try {
            return tTypeReference.getType().equals(String.class)?(T)str:objectMapper.readValue(str,tTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> T string2Obj(String str,Class<?> collectionClass,Class<T>... elements){
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(collectionClass,elements);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
