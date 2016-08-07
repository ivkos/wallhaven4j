package com.ivkos.wallhaven4j.models.enums;

import org.junit.Assert;
import org.junit.Test;

public class CategoryTest
{
   @Test
   public void ordinalsAreCorrect() throws Exception
   {
      Assert.assertEquals(0, Category.GENERAL.ordinal());
      Assert.assertEquals(1, Category.ANIME.ordinal());
      Assert.assertEquals(2, Category.PEOPLE.ordinal());
   }
}
