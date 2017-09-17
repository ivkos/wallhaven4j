package com.ivkos.wallhaven4j.models.misc.enums;

import org.junit.Test;

import static com.ivkos.wallhaven4j.models.misc.enums.ToplistRange.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ToplistRangeTest
{
   @Test
   public void allConstantsArePresent() throws Exception
   {
      assertNotNull(valueOf("LAST_1_DAY"));
      assertNotNull(valueOf("LAST_3_DAYS"));

      assertNotNull(valueOf("LAST_1_WEEK"));

      assertNotNull(valueOf("LAST_1_MONTH"));
      assertNotNull(valueOf("LAST_3_MONTHS"));
      assertNotNull(valueOf("LAST_6_MONTHS"));

      assertNotNull(valueOf("LAST_1_YEAR"));
   }

   @Test
   public void toAString() throws Exception
   {
      assertEquals("1d", LAST_1_DAY.toString());
      assertEquals("3d", LAST_3_DAYS.toString());

      assertEquals("1w", LAST_1_WEEK.toString());

      assertEquals("1M", LAST_1_MONTH.toString());
      assertEquals("3M", LAST_3_MONTHS.toString());
      assertEquals("6M", LAST_6_MONTHS.toString());

      assertEquals("1y", LAST_1_YEAR.toString());
   }
}
