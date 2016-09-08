package com.ivkos.wallhaven4j.models.misc.enums;

public enum Purity implements BitfieldCompatible
{
   NSFW, SKETCHY, SFW;

   @Override
   public long getBitfieldValue()
   {
      return 1 << ordinal();
   }
}
