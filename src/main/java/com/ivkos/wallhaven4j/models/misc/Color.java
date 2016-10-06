package com.ivkos.wallhaven4j.models.misc;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Color
{
   private final int red;
   private final int green;
   private final int blue;

   /**
    * Constructs a new Color object from the specified RGB values
    *
    * @param red   red value
    * @param green green value
    * @param blue  blue value
    * @throws IllegalArgumentException if any of the color values are out of the valid ranges
    */
   public Color(int red, int green, int blue)
   {
      checkArgument(red >= 0 && red <= 255, "red must be between 0 and 255");
      checkArgument(green >= 0 && green <= 255, "green must be between 0 and 255");
      checkArgument(blue >= 0 && blue <= 255, "blue must be between 0 and 255");

      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   /**
    * Constructs a new Color object from the specified RGB hex string
    *
    * @param hex hex string
    * @throws NullPointerException     if the specified string is null
    * @throws IllegalArgumentException if the hex string is invalid
    */
   public Color(String hex)
   {
      checkNotNull(hex, "hex must not be null");
      checkArgument(hex.matches("#?[0-9A-Fa-f]{6}"), "invalid hex string (%s)", hex);

      int offset = hex.startsWith("#") ? 1 : 0;

      red = Integer.parseInt(hex.substring(offset, 2 + offset), 16);
      green = Integer.parseInt(hex.substring(2 + offset, 4 + offset), 16);
      blue = Integer.parseInt(hex.substring(4 + offset, 6 + offset), 16);
   }

   /**
    * Returns a hex string (#rrggbb) representing the color
    *
    * @return hex string representing the color
    */
   public String toHex()
   {
      return String.format("#%02X%02X%02X", red, green, blue);
   }

   /**
    * Returns an <code>rgb(R, G, B)</code> string representing the color
    *
    * @return rgb(...) string representing the color
    */
   public String toRgb()
   {
      return String.format("rgb(%d,%d,%d)", red, green, blue);
   }

   public int getRed()
   {
      return red;
   }

   public int getGreen()
   {
      return green;
   }

   public int getBlue()
   {
      return blue;
   }

   /**
    * Returns the hex string (#rrggbb) representing the color
    *
    * @return hex string representing the color
    */
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
