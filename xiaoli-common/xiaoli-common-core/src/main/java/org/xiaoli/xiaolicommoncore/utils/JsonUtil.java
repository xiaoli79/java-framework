package org.xiaoli.xiaolicommoncore.utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtil {

    private static ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
                .configure(MapperFeature.USE_ANNOTATIONS, false)
                .addModule(new JavaTimeModule())
                .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) // TODO 魔法值需要统一管理并加上有效注释
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

//  序列化--对象转Json
    public static <T> String obj2String(T obj) {

        if(obj == null){
            return null;
        }

        try{
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("对象转Json失败{}",e.getMessage());
            return null;
        }
    }

    //  序列化(格式化)--对象转Json
    public static <T> String obj2StringPretty(T obj) {

        if(obj == null){
            return null;
        }
        try{
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("对象转Json失败{}",e.getMessage());
            return null;
        }
    }


    // 反序列化--Json字符串转对象~~

    public static<T>T string2Obj(String str, Class<T> clazz){

        if(str == null || str.isEmpty() || clazz == null){
            return null;
        }
        try{
            return clazz.equals(String.class) ? (T)str : OBJECT_MAPPER.readValue(str,clazz);
        } catch (JsonProcessingException e) {
            log.warn("Json字符串转对象失败：{}",e.getMessage());
            return null;
        }
    }


}
