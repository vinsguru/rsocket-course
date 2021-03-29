package com.vinsguru.rsocket.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;

public class ObjectUtil {

    public static Payload toPayload(Object o){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(o);
            return DefaultPayload.create(bytes);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(Payload payload, Class<T> type){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = payload.getData().array();
            return objectMapper.readValue(bytes, type);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
