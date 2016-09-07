package com.ivkos.wallhaven4j.models.misc;

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

      if ((w == 7 && h == 3) || (w == 3 && h == 7)) {
         w *= 3;
         h *= 3;
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
}
