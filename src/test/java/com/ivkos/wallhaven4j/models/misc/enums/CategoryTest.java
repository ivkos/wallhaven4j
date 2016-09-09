package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Category.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryTest
{
   @Test
   public void valuesAreCorrect() throws Exception
   {
      assertEquals(4, GENERAL.getBitfieldValue());
      assertEquals(2, ANIME.getBitfieldValue());
      assertEquals(1, PEOPLE.getBitfieldValue());
   }

   @Test
   public void constantsArePresent() throws Exception
   {
      assertNotNull(Category.valueOf("GENERAL"));
      assertNotNull(Category.valueOf("ANIME"));
      assertNotNull(Category.valueOf("PEOPLE"));
   }
}
