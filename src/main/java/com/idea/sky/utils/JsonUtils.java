package com.idea.sky.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * josn数据转换工具类
 *
 * @author 401681
 */
public class JsonUtils<K, V> {
    private static final String PAGE_TOTAL = "total";
    private static final String PAGE_ROWS = "rows";

    private static final ObjectMapper mapper = new ObjectMapper();
    public static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 将json数据转成指定类型的对象
     *
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T parseJsonToObject(String content, Class<T> valueType) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return JSON.parseObject(content, valueType);
    }

    /**
     * 将Object对象转成指定类型的对象
     *
     * @param object
     * @param objectType
     * @return
     */
    public static <T> T parseEntityToObject(Object object, Class<T> objectType) {
        try {
            return object == null ? null : JSON.parseObject(JSON.toJSONString(object), objectType);
        } catch (Exception e) {
            logger.error("json解析错误:" + e.getMessage());
        }
        return null;
    }

    /**
     * 将对象格式化成json格式数据
     *
     * @param obj
     * @return
     */
    public static String parseObjectToJson(Object obj) {
        return JSON.toJSONString(obj, true);
    }

    /**
     * 将json格式数据转成Map对象
     *
     * @param content
     * @return
     */
    public static Map<String, Object> parseJsonStringToMap(Object content) {
        try {
            return mapper.readValue(JsonUtils.toJsonString(content), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            logger.error("json解析错误:" + e.getMessage());
        }
        return null;
    }

    /**
     * 将Object格式数据转成JSONObject对象
     *
     * @param object
     * @return
     */
    public static JSONObject parseEntityToJSONObject(Object object) {
        try {
            return object == null ? new JSONObject() : JSONObject.parseObject(JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("json解析错误:" + e.getMessage());
        }
        return null;
    }

    /**
     * 将后台传过来的数据封装返回前台
     *
     * @param total      总条数
     * @param resultList 分页数据集
     * @return
     */
    public static Map<String, Object> resultMap(Long total, Collection<?> resultList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(PAGE_TOTAL, total);
        map.put(PAGE_ROWS, resultList);
        return map;
    }

    /**
     * 将后台传过来的数据封装返回前台
     *
     * @param total      总条数
     * @param resultList 分页数据集
     * @return
     */
    public static Map<String, Object> resultMap(Long total, Collection<?> resultList, Map<String, Object> resultData) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(PAGE_TOTAL, total);
        map.put(PAGE_ROWS, resultList);
        if (!resultData.isEmpty()) {
            Iterator iterator = resultData.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                map.put(entry.getKey().toString(), entry.getValue());
            }
        }
        return map;
    }

    /**
     * 将 Java 对象转为 JSON 字符串
     */
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Java 转 JSON 出错！", e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    public static <T> T parseToEntity(String json, Class<T> type) {
        T obj;
        try {
            obj = mapper.readValue(json, type);
        } catch (Exception e) {
            logger.error("JSON 转 Java 出错！", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 转换成json
     * 默认将class标记序列化进去
     */
    public static String toJsonString(Object obj) {
        return toJsonString(obj, true);
    }

    /**
     * 转换成json
     */
    public static String toJsonString(Object obj, boolean isPrettyFormat) {
        if (obj != null) {
            if (obj instanceof String && StringUtils.isBlank((String) obj)) {
                return "";
            }
            try {
                if (isPrettyFormat) {
                    return JSON.toJSONString(obj, true);
                } else {
                    return JSON.toJSONString(obj);
                }
            } catch (Exception e) {
                return "null";
            }
        }
        return "null";
    }

    /***
     * json转javaBean对象
     *
     * @param json
     * @return
     */
    public static Object jsonParseObject(String json) {
        if (StringUtils.isBlank(json)) {//字符串为空时返回空串
            return "";
        } else if (StringUtils.equalsIgnoreCase(json, "null")) {
            return null;
        }
        return JSON.parse(json);
    }

    /**
     * json转javaBean对象
     *
     * @param json
     * @param clazz
     * @return 如果为null、空字符串或者'null'字符串，将返回null
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将string类型的 list集合转换为List<对象集合>
     *
     * @param text
     * @param clazz
     * @return 如果传入的值为空, 返回空的集合
     */
    public static <T> List<T> parseToArray(Object text, Class<T> clazz) {
        if (text != null) {
            return JSON.parseArray(text.toString(), clazz);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 将json格式的list集合转换为list对象,与parse方法一样
     *
     * @param text
     * @param <T>
     * @return
     */
    public static <T> List<T> parseToArray(Object text) {
        if (StringUtils.isBlank((String) text)) {
            return null;
        }
        try {
            return (List<T>) mapper.readValue(toJSON(text), new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
