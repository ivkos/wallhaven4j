package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.BitfieldCompatible.bitfieldSumOf;
import static org.junit.Assert.assertEquals;

public class BitfieldCompatibleTest
{
   @Test
   public void bitfieldSum() throws Exception
   {
      assertEquals(0, bitfieldSumOf());
      assertEquals(4, bitfieldSumOf(Purity.SFW));
      assertEquals(5, bitfieldSumOf(Purity.SFW, Purity.NSFW));
      assertEquals(7, bitfieldSumOf(Purity.SFW, Purity.SKETCHY, Purity.NSFW));
   }
}
