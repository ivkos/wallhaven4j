package com.ivkos.wallhaven4j.models.misc;

import org.junit.Assert;
import org.junit.Test;

public class RatioTest
{
   private Ratio ratio = new Ratio(16, 9);

   @Test(expected = IllegalArgumentException.class)
   public void ratioWontAcceptZeroWidth() throws Exception
   {
      new Ratio(0, 1);
   }

   @Test(expected = IllegalArgumentException.class)
   public void ratioWontAcceptZeroHeight() throws Exception
   {
      new Ratio(1, 0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void ratioWontAcceptNegativeWidth() throws Exception
   {
      new Ratio(-2, 1);
   }

   @Test(expected = IllegalArgumentException.class)
   public void ratioWontAcceptNegativeHeight() throws Exception
   {
      new Ratio(1, -2);
   }

   @Test
   public void getWidth() throws Exception
   {
      Assert.assertEquals(16, ratio.getWidth());
   }

   @Test
   public void getHeight() throws Exception
   {
      Assert.assertEquals(9, ratio.getHeight());
   }

   @Test
   public void toAString() throws Exception
   {
      Assert.assertEquals("16x9", ratio.toString());
   }

   @Test
   public void thatEquals() throws Exception
   {
      Assert.assertTrue(new Ratio(16, 9).equals(new Ratio(16, 9)));
      Assert.assertFalse(new Ratio(9, 16).equals(new Ratio(16, 9)));
   }

   @Test
   public void thatHashCodeIsEqual() throws Exception
   {
      Assert.assertTrue(new Ratio(16, 9).hashCode() == new Ratio(16, 9).hashCode());
      Assert.assertTrue(new Ratio(16, 9).hashCode() == new Ratio(32, 18).hashCode());

      Assert.assertFalse(new Ratio(1080, 1080).hashCode() == new Ratio(1920, 1080).hashCode());
   }
}
