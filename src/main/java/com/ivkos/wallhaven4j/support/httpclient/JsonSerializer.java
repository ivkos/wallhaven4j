package com.ivkos.wallhaven4j.support.httpclient;

public interface JsonSerializer
{
   String toJson(Object src);

   <T> T fromJson(String json, Class<T> type);
}
