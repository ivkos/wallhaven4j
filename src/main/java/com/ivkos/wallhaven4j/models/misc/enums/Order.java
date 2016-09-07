package com.ivkos.wallhaven4j.models.misc.enums;

public enum Order
{
   ASC, DESC;

   public String getValue()
   {
      return this.name().toLowerCase();
   }
}
