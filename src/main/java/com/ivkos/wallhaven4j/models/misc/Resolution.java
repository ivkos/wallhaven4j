package com.ivkos.wallhaven4j.models.misc;

import java.util.Objects;

public class Resolution extends Ratio
{
   public Resolution(long width, long height)
   {
      super(width, height);
   }

   public Ratio getRatio()
   {
      if (getWidth() == getHeight()) {
         return new Ratio(1, 1);
      }

      long divisor = gcd(getWidth(), getHeight());

      long w = getWidth() / divisor;
      long h = getHeight() / divisor;

      //region handle some ratios having better known terms than their "real" ones
      if ((w == 8 && h == 5) || (w == 5 && h == 8)) {
         w *= 2;
         h *= 2;
      }

      if (w == 64 && h == 27) {
         w = 21;
         h = 9;
      }

      if (w == 27 && h == 64) {
         w = 9;
         h = 21;
      }
      //endregion

      return new Ratio(w, h);
   }

   private long gcd(long a, long b)
   {
      if (b == 0) return a;
      else return gcd(b, a % b);
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
