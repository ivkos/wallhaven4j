package com.ivkos.wallhaven4j.models.misc.enums;

public enum Category
{
   GENERAL(4), ANIME(2), PEOPLE(1);

   private final long value;

   Category(long value)
   {
      this.value = value;
   }

   public long getValue()
   {
      return value;
   }
}
