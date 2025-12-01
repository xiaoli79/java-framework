package org.xiaoli.xiaolicommonredis.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.xiaoli.xiaolicommoncore.utils.JsonUtil;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
//    ************************操作String类型*********************************8

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
//      判断得到的对象是否为空~~
        if(o == null){
            return null;
        }
//      先序列化
        String jsonStr = JsonUtil.obj2String(o);
//      反序列化
        return JsonUtil.string2Obj(jsonStr,clazz);

    }

//  得到键对应的值  （将缓存的数据反序列化为指定类型返回）
    public <T> T getCacheObject(final String key, TypeReference<T> valueTypeRef){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);
//      判断o是否为空
        if(o == null){
            return null;
        }
        String jsonStr = JsonUtil.obj2String(o);
        return JsonUtil.string2Obj(jsonStr,valueTypeRef);
    }


    //************************操作List类型******************************
    /**
     * 缓存List数据
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     * @param <T> 对象类型
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 从List结构左侧插入数据
     * @param key
     * @param dataList
     * @param <T>
     */


    public <T> void leftPushForList(final String key, final List<T> dataList) {
        redisTemplate.opsForList().leftPush(key, dataList);
    }

    /**从List结构右侧插入数据
     * 从List
     * @param key
     * @param dataList
     * @param <T>
     */

    public <T> void rightPushForList(final String key, final List<T> dataList) {
        redisTemplate.opsForList().rightPush(key, dataList);
    }

    /**
     * 删除左侧第一个数据
     * @param key
     */
    public void leftPopForList(final String key) {
        redisTemplate.opsForList().leftPop(key);
    }


    /**
     * 删除右侧第一个数据
     * @param key
     */
    public void rightPopForList(final String key) {
        redisTemplate.opsForList().rightPop(key);
    }


    /**
     * 移除List第一个匹配的元素
     *
     * @param key key
     * @param value 值
     * @param <T> 值类型
     */
    public <T> void removeForList(final String key, T value) {
        redisTemplate.opsForList().remove(key, 1L, value);
    }
    //⬆️
    //如果count > 0 ，就是从左向右删除count个与value匹配的元素
    //如果count ==0 ，就是删除所有与value相关的元素
    //如果count < 0 , 就是从右向左删除count个与value匹配的元素

    /**
     * 移除List中匹配第所有列表元素
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void removeForAllList(final String key,T value) {
        redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 移除key下所有列表元素
     * @param key
     */

    public void removeForAllList(final String key) {
        redisTemplate.opsForList().trim(key, -1,0);
    }

    /**
     * 根据下表进行更新列表中的元素
     * @param key
     * @param index
     * @param value
     * @param <T>
     */

    public <T> void setElementAtIndex(final String key, final int index, final T value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 获得缓存的list对象,list中不能有模板
     * @param key key 缓存的键值
     * @param clazz 对象的类
     * @return 列表
     * @param <T> 对象类型
     */
    public <T> List<T> getCacheList(final String key, Class<T> clazz) {
        List list = redisTemplate.opsForList().range(key, 0, -1);
        return JsonUtil.string2List(JsonUtil.obj2String(list), clazz);
    }

    /**
     * 获得缓存的list对象,list中可以支持复杂模板类
     * @param key key信息
     * @param typeReference 类型模板
     * @return list对象
     * @param <T> 对象类型
     */
    public <T> List<T> getCacheList(final String key, TypeReference<List<T>> typeReference) {
        List list = redisTemplate.opsForList().range(key, 0, -1);
        List<T> res = JsonUtil.string2Obj(JsonUtil.obj2String(list), typeReference);
        return res;
    }

    /**
     * 根据范围获取List
     *
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     * @param clazz 类信息
     * @return List列表
     * @param <T> 类型
     */
    public <T> List<T> getCacheListByRange(final String key, long start, long end, Class<T> clazz) {
        List range = redisTemplate.opsForList().range(key, start, end);
        return JsonUtil.string2List(JsonUtil.obj2String(range), clazz);
    }

    /**
     * 根据范围获取List
     *
     * @param key key
     * @param start 开始
     * @param end 结果
     * @param typeReference 类型模板
     * @return list列表
     * @param <T> 类型信息
     */
    public <T> List<T> getCacheListByRange(final String key, long start, long end, TypeReference<List<T>> typeReference) {
        List range = redisTemplate.opsForList().range(key, start, end);
        return JsonUtil.string2Obj(JsonUtil.obj2String(range), typeReference);
    }

    /**
     * 获取指定列表长度
     * @param key key信息
     * @return 列表长度
     */
    public long getCacheListSize(final String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0L : size;
    }
}
