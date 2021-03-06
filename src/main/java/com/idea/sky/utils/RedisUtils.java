// package com.idea.sky.utils;
//
// import com.alibaba.fastjson.JSON;
// import com.sky.blog.entity.BlogReply;
// import javafx.scene.chart.ValueAxis;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Component;
// import org.springframework.util.CollectionUtils;
// import org.apache.poi.ss.formula.functions.T;
//
// import java.util.*;
// import java.util.concurrent.TimeUnit;
//
// /**
//  * @author Administrator
//  */
// @Component(value = "redisUtils")
// public class RedisUtils<T, V> {
//
//     /**
//      * 如果这个Qualifier 里面的value redisTemplate 报红可以忽略不计
//      */
//     @Autowired
//     @Qualifier(value = "redisTemplate")
//     private RedisTemplate redisTemplate;
//
//     /**
//      * 设置指定过期时间
//      *
//      * @param key
//      * @param time
//      * @return
//      */
//     public Boolean setExpire(String key, Long time) {
//
//         try {
//             if (time > 0) {
//                 redisTemplate.expire(key, time, TimeUnit.SECONDS);
//             }
//             return true;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 根据key获取数据过期时间
//      *
//      * @param key
//      * @return
//      */
//     public long getExpire(String key) {
//         return redisTemplate.getExpire(key, TimeUnit.SECONDS);
//     }
//
//     /**
//      * 清楚缓存
//      * 根据传入的key的数量自行判断
//      *
//      * @param keys
//      */
//     public void deleteCache(String... keys) {
//         if (keys != null && keys.length > 0) {
//             if (keys.length == 1) {
//                 redisTemplate.delete(keys[0]);
//             } else {
//                 redisTemplate.delete(CollectionUtils.arrayToList(keys));
//             }
//         }
//     }
//
//     /**
//      * 获取普通键的值
//      *
//      * @param key
//      * @return
//      */
//     public Object getNormalValue(String key) {
//         return key != null ? redisTemplate.opsForValue().get(key) : null;
//     }
//
//     /**
//      * 设置普通的值
//      *
//      * @param key
//      * @param value
//      * @param time
//      * @return
//      */
//     public Boolean setNormalValue(String key, Object value, Long time) {
//         try {
//             if (key != null && value != null && time == null) {
//                 redisTemplate.opsForValue().set(key, value);
//             } else if (key != null && value != null && time != null) {
//                 redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//             }
//             return true;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 递增
//      *
//      * @param key             键
//      * @param recursionFactor 每次递增的值
//      * @return
//      */
//     public long increment(String key, Long recursionFactor) {
//         if (recursionFactor < 0) {
//             throw new RuntimeException("递归因子必须大于0");
//         }
//         return redisTemplate.opsForValue().increment(key, recursionFactor);
//     }
//
//     /**
//      * 递减
//      *
//      * @param key             键
//      * @param recursionFactor 每次递减的值
//      * @return
//      */
//     public long decrement(String key, Long recursionFactor) {
//         if (recursionFactor < 0) {
//             throw new RuntimeException("递归因子必须大于0");
//         }
//         return redisTemplate.opsForValue().decrement(key, recursionFactor);
//     }
//
//     /**
//      * 设置hash
//      *
//      * @param key
//      * @param hashKeys
//      * @param value
//      * @return
//      */
//     public boolean setHash(String key, Object[] hashKeys, Object... value) {
//         try {
//             if (key != null && hashKeys != null && value != null) {
//                 if (hashKeys.length == 1 && value.length == 1) {
//                     redisTemplate.opsForHash().putIfAbsent(key, hashKeys[0], value[0]);
//                     return true;
//                 } else if (hashKeys.length > 1 && value.length > 1) {
//                     for (int i = 0; i < value.length; i++) {
//                         redisTemplate.opsForHash().putIfAbsent(key, hashKeys[i], value[i]);
//                     }
//                     return true;
//                 }
//             }
//             return false;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 根据键和hash键获取对象
//      *
//      * @param key
//      * @param hashKey
//      * @return
//      */
//     public Object getHash(String key, String hashKey) {
//         if (key != null && hashKey != null) {
//             return redisTemplate.opsForHash().get(key, hashKey);
//         }
//         return null;
//     }
//
//     /**
//      * 根据键获取所有的hash对象
//      *
//      * @param key
//      * @return
//      */
//     public Object getAllHash(String key) {
//         if (key != null) {
//             return redisTemplate.opsForHash().entries(key);
//         }
//         return null;
//     }
//
//     /**
//      * 根据条件获取一个对象或者获取多个对象
//      *
//      * @param key
//      * @param hashKey
//      * @return
//      */
//     public Object getValueOfField(String key, String... hashKey) {
//         if (key != null && hashKey != null && hashKey.length > 0) {
//             // 如果它的length等于一的话说明只传入了一个值,我们只需要用hget就行了
//             if (hashKey.length == 1) {
//                 return redisTemplate.opsForHash().get(key, hashKey[0]);
//             }
//             if (hashKey.length > 1) {
//                 Collection<Object> list = new ArrayList<>();
//                 for (int i = 0; i < hashKey.length; i++) {
//                     list.add(hashKey[i]);
//                 }
//                 return redisTemplate.opsForHash().multiGet(key, list);
//             }
//         }
//         return null;
//     }
//
//     /**
//      * 删除指定key中的指定field域或者多个field域
//      *
//      * @param key
//      * @param hashKey
//      * @return
//      */
//     public Boolean deleteValueOfField(String key, String... hashKey) {
//         try {
//             if (key != null && hashKey != null && hashKey.length > 0) {
//                 if (hashKey.length == 1) {
//                     redisTemplate.opsForHash().delete(key, hashKey[0]);
//                     return true;
//                 }
//
//                 if (hashKey.length > 1) {
//                     for (int i = 0; i < hashKey.length; i++) {
//                         redisTemplate.opsForHash().delete(key, hashKey[i]);
//                     }
//                     return true;
//                 }
//             }
//             return false;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 获取hash key元素中的数量
//      *
//      * @param key
//      * @param hashKey
//      * @return
//      */
//     public Long getHashLength(String key, String hashKey) {
//         try {
//             if (key != null && hashKey != null) {
//                 return redisTemplate.opsForHash().lengthOfValue(key, hashKey);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//         return null;
//     }
//
//     /**
//      * 绑定值
//      *
//      * @param key
//      * @param values
//      */
//     public void addArrayList(String key, List<T> values) {
//         redisTemplate.boundValueOps(key).set(JSON.toJSON(values).toString());
//     }
//
//     public Object getArrayList(String key) {
//         return redisTemplate.boundValueOps(key).get();
//     }
//
//     /**
//      * 添加内容到set
//      *
//      * @param key
//      * @param values
//      */
//     public void addValueToSet(String key, List<T> values) {
//         if (key != null) {
//             for (T value : values) {
//                 redisTemplate.opsForSet().add(key, JsonUtils.toJsonString(value));
//             }
//         }
//
//     }
//
//     /**
//      * 获取set集合中的内容
//      *
//      * @param key
//      * @return
//      */
//     public Set getValueOfSet(String key) {
//         if (key != null) {
//             return redisTemplate.opsForSet().members(key);
//         }
//         return null;
//     }
//
//     /**
//      * 将hashmap对象放入redis数据库中
//      *
//      * @param key
//      * @param map
//      */
//     public void addMap(String key, Map<T, V> map) {
//         if (key != null) {
//             redisTemplate.boundValueOps(key).set(JSON.toJSONString(map));
//         }
//     }
//
//     /**
//      * 获取转换为string类型的map集合对象
//      *
//      * @param key
//      * @return
//      */
//     public Object getMap(String key) {
//         if (key != null) {
//             return redisTemplate.boundValueOps(key).get();
//         }
//         return null;
//     }
// }