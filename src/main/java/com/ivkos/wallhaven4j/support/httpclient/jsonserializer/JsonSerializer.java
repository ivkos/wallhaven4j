package com.ivkos.wallhaven4j.support.httpclient.jsonserializer;

public interface JsonSerializer
{
   String toJson(Object src);

   <T> T fromJson(String json, Class<T> type);
}
