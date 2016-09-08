package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Assert;
import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;

public class PurityTest
{
   @Test
   public void valuesAreCorrect() throws Exception
   {
      Assert.assertEquals(4, SFW.getBitfieldValue());
      Assert.assertEquals(2, SKETCHY.getBitfieldValue());
      Assert.assertEquals(1, NSFW.getBitfieldValue());
   }
}
