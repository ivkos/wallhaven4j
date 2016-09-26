package com.ivkos.wallhaven4j.models.misc.enums.util;

import java.util.Collection;
import java.util.EnumSet;

import static java.util.Arrays.asList;

public final class BitfieldSum
{
   private BitfieldSum()
   {
   }

   public static <E extends Enum<E> & BitfieldCompatible> long of(Collection<E> enumsCollection)
   {
      long result = 0;

      if (enumsCollection.isEmpty()) return result;

      EnumSet<E> enumSet = EnumSet.copyOf(enumsCollection);
      for (Enum<E> theEnum : enumSet) {
         BitfieldCompatible bfc = (BitfieldCompatible) theEnum;

         result += bfc.getBitfieldValue();
      }

      return result;
   }

   @SafeVarargs
   public static <E extends Enum<E> & BitfieldCompatible> long of(E... enums)
   {
      return of(asList(enums));
   }

   public static <E extends Enum<E> & BitfieldCompatible> String asThreeBitBinaryString(Collection<E> enumsCollection)
   {
      return String.format("%03d", Long.parseLong(Long.toBinaryString(of(enumsCollection))));
   }

   @SafeVarargs
   public static <E extends Enum<E> & BitfieldCompatible> String asThreeBitBinaryString(E... enums)
   {
      return asThreeBitBinaryString(asList(enums));
   }
}
