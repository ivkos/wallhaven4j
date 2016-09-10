package com.ivkos.wallhaven4j.util.jsonserializer;

import com.google.gson.Gson;
import com.google.inject.Inject;

import java.lang.reflect.Type;

public class GsonJsonSerializer implements JsonSerializer
{
   private final Gson gson;

   @Inject
   GsonJsonSerializer(Gson gson)
   {
      this.gson = gson;
   }

   @Override
   public String toJson(Object src)
   {
      return gson.toJson(src);
   }

   @Override
   public <T> T fromJson(String json, Class<T> type)
   {
      return gson.fromJson(json, type);
   }

   @Override
   public <T> T fromJson(String json, Type typeOfT)
   {
      return gson.fromJson(json, typeOfT);
   }
}
