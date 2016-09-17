package com.ivkos.wallhaven4j.models.misc;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class Ratio
{
   private final long width;
   private final long height;

   /**
    * Creates a new <code>Ratio</code>.
    *
    * @param width  the width
    * @param height the height
    * @throws IllegalArgumentException if the width or height are not greater than zero
    */
   public Ratio(long width, long height)
   {
      checkArgument(width > 0, "width must be greater than zero");
      checkArgument(height > 0, "height must be greater than zero");

      this.width = width;
      this.height = height;
   }

   /**
    * Returns the width.
    *
    * @return the width
    */
   public long getWidth()
   {
      return width;
   }

   /**
    * Returns the height.
    *
    * @return the height
    */
   public long getHeight()
   {
      return height;
   }

   /**
    * Returns a string representation in the format of <code>WxH</code>.
    *
    * @return a string representation in the format of <code>WxH</code>
    */
   @Override
   public String toString()
   {
      return width + "x" + height;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof Ratio)) return false;

      Ratio that = (Ratio) o;

      return Objects.equals((double) width / height, (double) that.width / that.height);
   }

   @Override
   public int hashCode()
   {
      return Objects.hash((double) width / height);
   }
}
