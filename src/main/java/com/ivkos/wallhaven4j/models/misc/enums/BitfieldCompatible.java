package com.ivkos.wallhaven4j.models.misc.enums;

import java.util.Collection;
import java.util.EnumSet;

import static java.util.Arrays.asList;

public interface BitfieldCompatible
{
   static <E extends Enum<E> & BitfieldCompatible> long bitfieldSumOf(Collection<E> enumsCollection)
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
   static <E extends Enum<E> & BitfieldCompatible> long bitfieldSumOf(E... enums)
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
