package org.xiaoli.xiaolicommoncore.utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 对象转Json格式字符串
     * @param obj 对象
     * @return Json格式字符串
     * @param <T> 对象类型
     */
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


    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     * @param obj 对象
     * @return 美化的Json格式字符串
     * @param <T> 对象类型
     */
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


    /**
     * 字符串转换为自定义对象
     * @param str 要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     * @param <T> 对象类型
     */
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

    /**
     * 字符串转换为自定义对象,支持复杂的泛型嵌套
     *
     * @param str json字符串
     * @param valueTypeRef 对象模板信息
     * @return 对象类对应的对象
     * @param <T> 对象类
     */

    public static <T> T string2Obj(String str, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(str) || valueTypeRef == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str,valueTypeRef);
        } catch (JsonProcessingException e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }


    /**
     * 字符串转换为自定义字段转为list,支持List嵌套简单对象
     * @param str json字符串
     * @param clazz 对象类
     * @return 对象列表
     * @param <T> 对象类型
     */

//  解决泛型擦除问题~
    public static <T> List<T> string2List(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (JsonProcessingException e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为自定义字段转为map,支持Map嵌套简单对象
     * @param str  str 字符串信息
     * @param valueClass valueClass value的类别
     * @return   map对象
     * @param <T> value 的类型
     */

    //  解决泛型擦除问题~
    public static <T> Map<String, T> string2Map(String str, Class<T> valueClass){
        if (StringUtils.isEmpty(str) || valueClass == null) {
            return null;
        }
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, valueClass);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (JsonProcessingException e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }
}


