package com.ivkos.wallhaven4j.models.misc.enums;

public enum ToplistRange
{
   LAST_1_DAY("1d"),
   LAST_3_DAYS("3d"),

   LAST_1_WEEK("1w"),

   LAST_1_MONTH("1M"),
   LAST_3_MONTHS("3M"),
   LAST_6_MONTHS("6M"),

   LAST_1_YEAR("1y");

   private final String str;

   ToplistRange(String str)
   {
      this.str = str;
   }

   @Override
   public String toString()
   {
      return str;
   }
}
