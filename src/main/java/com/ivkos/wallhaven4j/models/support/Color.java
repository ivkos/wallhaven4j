package com.ivkos.wallhaven4j.models.support;

import java.util.Objects;

public class Color
{
   private final int red;
   private final int green;
   private final int blue;

   public Color(int red, int green, int blue)
   {
      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   public Color(String hex)
   {
      int offset = hex.startsWith("#") ? 1 : 0;

      red = Integer.parseInt(hex.substring(offset, 2 + offset), 16);
      green = Integer.parseInt(hex.substring(2 + offset, 4 + offset), 16);
      blue = Integer.parseInt(hex.substring(4 + offset, 6 + offset), 16);
   }

   public String toHex()
   {
      return String.format("#%02X%02X%02X", red, green, blue);
   }

   public String toRgb()
   {
      return String.format("rgb(%d,%d,%d)", red, green, blue);
   }

   @Override
   public String toString()
   {
      return toHex();
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof Color)) return false;
      Color color = (Color) o;
      return red == color.red &&
            green == color.green &&
            blue == color.blue;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(red, green, blue);
   }
}
