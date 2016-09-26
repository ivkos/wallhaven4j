package com.ivkos.wallhaven4j.models.misc.enums.util;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;
import static org.junit.Assert.assertEquals;

public class BitfieldSumTest
{
   @Test
   public void bitfieldSum() throws Exception
   {
      assertEquals(0, BitfieldSum.of());

      assertEquals(SFW.getBitfieldValue(),
            BitfieldSum.of(SFW));

      assertEquals(SFW.getBitfieldValue() + NSFW.getBitfieldValue(),
            BitfieldSum.of(SFW, NSFW));

      assertEquals(SFW.getBitfieldValue() + SKETCHY.getBitfieldValue() + NSFW.getBitfieldValue(),
            BitfieldSum.of(SFW, SKETCHY, NSFW));
   }

   @Test
   public void sumOfDuplicates() throws Exception
   {
      assertEquals(SFW.getBitfieldValue(), BitfieldSum.of(SFW, SFW));
   }

   @Test
   public void asBinaryString() throws Exception
   {
      assertEquals("000", BitfieldSum.asThreeBitBinaryString());
      assertEquals("001", BitfieldSum.asThreeBitBinaryString(NSFW));
      assertEquals("101", BitfieldSum.asThreeBitBinaryString(SFW, NSFW));
   }
}
