package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Assert;
import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Category.*;

public class CategoryTest
{
   @Test
   public void valuesAreCorrect() throws Exception
   {
      Assert.assertEquals(4, GENERAL.getBitfieldValue());
      Assert.assertEquals(2, ANIME.getBitfieldValue());
      Assert.assertEquals(1, PEOPLE.getBitfieldValue());
   }
}
