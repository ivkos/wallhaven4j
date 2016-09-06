package com.ivkos.wallhaven4j.models.misc;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest
{
   private Color red = new Color(255, 0, 0);
   private Color abbeef = new Color("#abbeef");

   @Test
   public void toHex() throws Exception
   {
      Assert.assertEquals("#FF0000", red.toHex());
      Assert.assertEquals("#ABBEEF", abbeef.toHex());
   }

   @Test
   public void toRgb() throws Exception
   {
      Assert.assertEquals("rgb(255,0,0)", red.toRgb());
      Assert.assertEquals("rgb(171,190,239)", abbeef.toRgb());
   }

   @Test
   public void toAString() throws Exception
   {
      Assert.assertEquals(red.toHex(), red.toString());
   }

   @Test
   public void thatEquals() throws Exception
   {
      Color red1 = new Color(255, 0, 0);
      Color red2 = new Color("FF0000");

      Assert.assertTrue(red1.equals(red2));
   }

   @Test
   public void thatHashCodeIsEqual() throws Exception
   {
      Color red1 = new Color(255, 0, 0);
      Color red2 = new Color("FF0000");

      Assert.assertTrue(red1.hashCode() == red2.hashCode());
   }
}
