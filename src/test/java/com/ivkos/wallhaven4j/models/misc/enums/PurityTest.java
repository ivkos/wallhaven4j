package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PurityTest
{
   @Test
   public void valuesAreCorrect() throws Exception
   {
      assertEquals(4, SFW.getBitfieldValue());
      assertEquals(2, SKETCHY.getBitfieldValue());
      assertEquals(1, NSFW.getBitfieldValue());
   }

   @Test
   public void constantsArePresent() throws Exception
   {
      assertNotNull(Purity.valueOf("SFW"));
      assertNotNull(Purity.valueOf("SKETCHY"));
      assertNotNull(Purity.valueOf("NSFW"));
   }
}
