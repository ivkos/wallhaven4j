package com.ivkos.wallhaven4j.models.misc.enums;

public enum Category implements BitfieldCompatible
{
   PEOPLE, ANIME, GENERAL;

   @Override
   public long getBitfieldValue()
   {
      return 1 << ordinal();
   }
}
