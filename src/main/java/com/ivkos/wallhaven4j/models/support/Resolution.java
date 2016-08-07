package com.ivkos.wallhaven4j.models.support;

import java.util.Objects;

public class Resolution
{
   private long width;
   private long height;

   public Resolution(long width, long height)
   {
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
