package com.ivkos.wallhaven4j.models.support;

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

}
