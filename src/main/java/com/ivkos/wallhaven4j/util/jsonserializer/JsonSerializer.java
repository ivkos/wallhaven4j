package com.ivkos.wallhaven4j.util.jsonserializer;

import java.lang.reflect.Type;

public interface JsonSerializer
{
   String toJson(Object src);

   <T> T fromJson(String json, Class<T> type);

   <T> T fromJson(String json, Type typeOfT);
}
