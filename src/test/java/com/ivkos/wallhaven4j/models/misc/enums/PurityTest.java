package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Assert;
import org.junit.Test;

public class PurityTest
{
   @Test
   public void ordinalsAreCorrect() throws Exception
   {
      Assert.assertEquals(0, Purity.SFW.ordinal());
      Assert.assertEquals(1, Purity.SKETCHY.ordinal());
      Assert.assertEquals(2, Purity.NSFW.ordinal());
   }
}
