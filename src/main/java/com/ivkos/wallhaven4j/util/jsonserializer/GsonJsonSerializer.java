package com.ivkos.wallhaven4j.util.jsonserializer;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class GsonJsonSerializer implements JsonSerializer
{
   private final Gson gson;

   @Inject
   public GsonJsonSerializer(Gson gson)
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
}
