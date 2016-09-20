package com.ivkos.wallhaven4j.models.misc.enums;

import static java.util.Arrays.asList;

public interface BitfieldCompatible
{
   static <E extends Enum<E> & BitfieldCompatible> long bitfieldSumOf(Iterable<Enum<E>> enums)
   {
      long result = 0;

      for (Enum<E> theEnum : enums) {
         BitfieldCompatible bfc = (BitfieldCompatible) theEnum;

         result += bfc.getBitfieldValue();
      }

      return result;
   }

   @SafeVarargs
   static <E extends Enum<E> & BitfieldCompatible> long bitfieldSumOf(Enum<E>... enums)
   {
      return bitfieldSumOf(asList(enums));
   }

   // Enums implicitly extend java.lang.Enum which implements ordinal()
   int ordinal();

   default long getBitfieldValue()
   {
      return 1 << ordinal();
   }
}
