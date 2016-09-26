package com.ivkos.wallhaven4j.models.misc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResolutionTest
{
   private Resolution resolution = new Resolution(1920, 1080);

   @Test
   public void parseResolution() throws Exception
   {
      Resolution r720p = Resolution.parse("1280x720");

      assertEquals(new Resolution(1280, 720), r720p);
   }

   @Test(expected = IllegalArgumentException.class)
   public void parseIllegalResolution() throws Exception
   {
      Resolution.parse("1280 x 720");
   }

   @Test(expected = IllegalArgumentException.class)
   public void parseNullResolution() throws Exception
   {
      Resolution.parse(null);
   }

   @Test(expected = IllegalArgumentException.class)
   public void parseEmptyResolution() throws Exception
   {
      Resolution.parse("");
   }

   @Test
   public void getWidth() throws Exception
   {
      assertEquals(1920, resolution.getWidth());
   }

   @Test
   public void getHeight() throws Exception
   {
      assertEquals(1080, resolution.getHeight());
   }

   @Test
   public void toAString() throws Exception
   {
      assertEquals("1920x1080", resolution.toString());
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
      assertEquals(new Ratio(16, 9), new Resolution(1920, 1080).getRatio());
      assertEquals(new Ratio(9, 16), new Resolution(1080, 1920).getRatio());

      assertEquals(new Ratio(4, 3), new Resolution(1024, 768).getRatio());

      assertEquals(new Ratio(16, 10), new Resolution(1920, 1200).getRatio());
      assertEquals(new Ratio(10, 16), new Resolution(1200, 1920).getRatio());

      assertEquals(new Ratio(8, 5), new Resolution(1920, 1200).getRatio());

      assertEquals(new Ratio(21, 9), new Resolution(2560, 1080).getRatio());
      assertEquals(new Ratio(9, 21), new Resolution(1080, 2560).getRatio());

      assertEquals(new Ratio(1, 1), new Resolution(800, 800).getRatio());
   }
}
