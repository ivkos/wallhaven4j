package com.ivkos.wallhaven4j.models.misc;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class Ratio
{
   private final long width;
   private final long height;

   public Ratio(long width, long height)
   {
      checkArgument(width > 0, "width must be greater than zero");
      checkArgument(height > 0, "height must be greater than zero");

      this.width = width;
      this.height = height;
   }

   public long getWidth()
   {
      return width;
   }

   public long getHeight()
   {
      return height;
   }

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
