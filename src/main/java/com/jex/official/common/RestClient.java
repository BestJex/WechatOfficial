package com.jex.official.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    private static ObjectMapper mapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    public static  <TResponse> TResponse get(String url, Class<TResponse> clazz) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            Response response = target.request().accept(MediaType.TEXT_PLAIN).get();
            TResponse tResponse = response.readEntity(clazz);
            return tResponse;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public static <TRequest, TResponse> TResponse postJSON(String url, TRequest request, Class<TResponse> clazz) {
        try{
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            Invocation invocation = target.request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON));
            Response response = invocation.invoke();
            TResponse tResponse = response.readEntity(clazz);
            return tResponse;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public static <TRequest, TResponse> TResponse postForm(String url, TRequest request, Class<TResponse> clazz) {
        try{
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            Invocation invocation = target.request().buildPost(Entity.entity(request, MediaType.APPLICATION_FORM_URLENCODED));
            Response response = invocation.invoke();
            TResponse tResponse = response.readEntity(clazz);
            return tResponse;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public static <T> String ObjectToString(T obj) throws Exception {
        if (obj == null) {
            return  "";
        }

        String requestJson = mapper.writeValueAsString(obj);

        return requestJson;
    }

    public static <T> T StringToObject(String json, Class<T> clazz) throws IOException {
        T obj = mapper.readValue(json, clazz);

        return obj;
    }
}
