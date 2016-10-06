package com.ivkos.wallhaven4j.models.misc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColorTest
{
   private Color red = new Color(255, 0, 0);
   private Color abbeef = new Color("#abbeef");

   @Test
   public void getRed() throws Exception
   {
      assertEquals(255, red.getRed());
      assertEquals(171, abbeef.getRed());
   }

   @Test
   public void getGreen() throws Exception
   {
      assertEquals(0, red.getGreen());
      assertEquals(190, abbeef.getGreen());
   }

   @Test
   public void getBlue() throws Exception
   {
      assertEquals(0, red.getBlue());
      assertEquals(239, abbeef.getBlue());
   }

   @Test
   public void toHex() throws Exception
   {
      assertEquals("#FF0000", red.toHex());
      assertEquals("#ABBEEF", abbeef.toHex());
   }

   @Test
   public void toRgb() throws Exception
   {
      assertEquals("rgb(255,0,0)", red.toRgb());
      assertEquals("rgb(171,190,239)", abbeef.toRgb());
   }

   @Test
   public void toAString() throws Exception
   {
      assertEquals(red.toHex(), red.toString());
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
