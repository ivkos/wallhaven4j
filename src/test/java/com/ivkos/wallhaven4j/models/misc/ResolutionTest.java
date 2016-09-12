package com.ivkos.wallhaven4j.models.misc;

import org.junit.Assert;
import org.junit.Test;

public class ResolutionTest
{
   private Resolution resolution = new Resolution(1920, 1080);

   @Test
   public void getWidth() throws Exception
   {
      Assert.assertEquals(1920, resolution.getWidth());
   }

   @Test
   public void getHeight() throws Exception
   {
      Assert.assertEquals(1080, resolution.getHeight());
   }

   @Test
   public void toAString() throws Exception
   {
      Assert.assertEquals("1920x1080", resolution.toString());
   }

   @Test
   public void thatEquals() throws Exception
   {
      Assert.assertTrue(new Resolution(1920, 1080).equals(new Resolution(1920, 1080)));
      Assert.assertFalse(new Resolution(1080, 1920).equals(new Resolution(1920, 1080)));
   }

   @Test
   public void thatHashCodeIsEqual() throws Exception
   {
      Assert.assertTrue(new Resolution(1920, 1080).hashCode() == new Resolution(1920, 1080).hashCode());
      Assert.assertFalse(new Resolution(1080, 1080).hashCode() == new Resolution(1920, 1080).hashCode());
   }

   @Test
   public void getRatio() throws Exception
   {
      Assert.assertEquals(new Ratio(16, 9), new Resolution(1920, 1080).getRatio());
      Assert.assertEquals(new Ratio(9, 16), new Resolution(1080, 1920).getRatio());

      Assert.assertEquals(new Ratio(4, 3), new Resolution(1024, 768).getRatio());

      Assert.assertEquals(new Ratio(16, 10), new Resolution(1920, 1200).getRatio());
      Assert.assertEquals(new Ratio(10, 16), new Resolution(1200, 1920).getRatio());

      Assert.assertEquals(new Ratio(8, 5), new Resolution(1920, 1200).getRatio());

      Assert.assertEquals(new Ratio(21, 9), new Resolution(2560, 1080).getRatio());

      Assert.assertEquals(new Ratio(1, 1), new Resolution(800, 800).getRatio());
   }
}
