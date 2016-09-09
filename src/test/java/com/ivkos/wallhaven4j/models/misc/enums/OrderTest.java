package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Order.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderTest
{
   @Test
   public void allConstantsArePresent() throws Exception
   {
      assertNotNull(valueOf("ASC"));
      assertNotNull(valueOf("DESC"));
   }

   @Test
   public void toStringIsCorrect() throws Exception
   {
      assertEquals("asc", ASC.toString());
      assertEquals("desc", DESC.toString());
   }
}
