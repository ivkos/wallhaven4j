package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.Sorting.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SortingTest
{
   @Test
   public void allConstantsArePresent() throws Exception
   {
      assertNotNull(valueOf("RELEVANCE"));
      assertNotNull(valueOf("RANDOM"));
      assertNotNull(valueOf("DATE_ADDED"));
      assertNotNull(valueOf("VIEWS"));
      assertNotNull(valueOf("FAVORITES"));
      assertNotNull(valueOf("TOPLIST"));
   }

   @Test
   public void toStringIsCorrect() throws Exception
   {
      assertEquals("relevance", RELEVANCE.toString());
      assertEquals("date_added", DATE_ADDED.toString());
   }
}
