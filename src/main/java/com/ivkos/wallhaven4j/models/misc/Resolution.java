package com.ivkos.wallhaven4j.models.misc;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class Resolution
{
   private final long width;
   private final long height;

   public Resolution(long width, long height)
   {
      Preconditions.checkArgument(width >= 0, "width must be positive");
      Preconditions.checkArgument(height >= 0, "height must be positive");

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
      if (!(o instanceof Resolution)) return false;
      Resolution that = (Resolution) o;
      return getWidth() == that.getWidth() &&
            getHeight() == that.getHeight();
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(getWidth(), getHeight());
   }
}
