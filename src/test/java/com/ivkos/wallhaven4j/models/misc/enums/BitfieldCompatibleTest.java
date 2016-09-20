package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.BitfieldCompatible.bitfieldSumOf;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;
import static org.junit.Assert.assertEquals;

public class BitfieldCompatibleTest
{
   @Test
   public void bitfieldSum() throws Exception
   {
      assertEquals(0, bitfieldSumOf());

      assertEquals(SFW.getBitfieldValue(),
            bitfieldSumOf(SFW));

      assertEquals(SFW.getBitfieldValue() + NSFW.getBitfieldValue(),
            bitfieldSumOf(SFW, NSFW));

      assertEquals(SFW.getBitfieldValue() + SKETCHY.getBitfieldValue() + NSFW.getBitfieldValue(),
            bitfieldSumOf(SFW, SKETCHY, NSFW));
   }

   @Test
   public void sumOfDuplicates() throws Exception
   {
      assertEquals(SFW.getBitfieldValue(), bitfieldSumOf(SFW, SFW));
   }
}
