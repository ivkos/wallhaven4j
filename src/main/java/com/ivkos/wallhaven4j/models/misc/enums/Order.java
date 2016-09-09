package com.ivkos.wallhaven4j.models.misc.enums;

public enum Order
{
   ASC, DESC;

   @Override
   public String toString()
   {
      return name().toLowerCase();
   }
}
