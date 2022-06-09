package com.idea.sky.utils.decode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;


/**
 * @author Administrator
 */
public class CustomJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public CustomJacksonHttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public CustomJacksonHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        // 这里是重点，增加支持的类型，看你的情况加
        // 我这里目前只需要加个 TEXT/PLAIN
        setSupportedMediaTypes(Arrays.asList(
//                MediaType.TEXT_PLAIN,
                MediaType.APPLICATION_JSON,
                new MediaType("application", "*+json"),
                MediaType.parseMediaType(MediaType.TEXT_PLAIN + ";charset=UTF-8")
                )
        );
    }
}
