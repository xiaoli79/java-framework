package org.xiaoli.xiaolicommonredis;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.xiaoli.xiaolicommoncore.utils.JsonUtil;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {



    @Autowired
    private RedisTemplate redisTemplate;

//  缓存String类型的数据~~
    public <T> void setCacheObject(final String key,final T value){
        //通常和方法的返回值一样~~
        redisTemplate.opsForValue().set(key,value);
    }

//  缓存String类型的数据~~并且设置有效时间
    public <T> void setCacheObject(final String key,final T value,final Long timeout,final TimeUnit timeUnit){
        //通常和方法的返回值一样~~
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }


//  如果没有key，则设置，如果有key,则不设置~~
    public <T> Boolean setCacheObjectIfAbsent(final String key,final T value){
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }


//  如果没有key，则设置，如果有key,则不设置~~ 并且设置有超时时间~~
    public <T> void setCacheObjectIfAbsent(final String key,final T value,final Long timeout,final TimeUnit timeUnit){
        //通常和方法的返回值一样~~
        redisTemplate.opsForValue().setIfAbsent(key,value,timeout,timeUnit);
    }

//  得到键对应的值  （将缓存的数据反序列化为指定类型返回）
    public <T> T getCacheObject(final String key,final Class<T> clazz){
        ValueOperations valueOperations = redisTemplate.opsForValue();


        Object o = valueOperations.get(key);

        if(o == null){
            return null;
        }

        String jsonStr = JsonUtil.obj2String(o);
        return JsonUtil.string2Obj(jsonStr,clazz);

    }

//  得到键对应的值  （将缓存的数据反序列化为指定类型返回）
    public <T> T getCacheObject(final String key, TypeReference<T> valueTypeRef){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);

        if(o == null){
            return null;
        }

        String jsonStr = JsonUtil.obj2String(o);
        return JsonUtil.string2Obj(jsonStr,valueTypeRef);

    }

}
