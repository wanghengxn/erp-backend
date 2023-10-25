package tech.qdhxy.erp.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

public class JsonUtil {

    @SneakyThrows
    public static String toJSON(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper.writeValueAsString(o);
    }
}
