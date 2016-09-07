package com.ivkos.wallhaven4j.models.misc.enums;

public enum Purity
{
   SFW(4), SKETCHY(2), NSFW(1);

   private final long value;

   Purity(long value)
   {
      this.value = value;
   }

   public long getValue()
   {
      return value;
   }
}
