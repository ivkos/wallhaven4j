package com.ivkos.wallhaven4j.models.misc;

import com.google.common.base.Preconditions;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Long.parseLong;

public class Resolution extends Ratio
{
   /**
    * Creates a new <code>Resolution</code>.
    *
    * @param width  the width
    * @param height the height
    * @throws IllegalArgumentException if the width or height are not greater than zero
    */
   public Resolution(long width, long height)
   {
      super(width, height);
   }

   /**
    * Parses a resolution string and returns a corresponding Resolution object
    *
    * @param resolutionString resolution string in the format of WxH
    * @return the resolution object
    */
   public static Resolution parse(String resolutionString)
   {
      Preconditions.checkArgument(!isNullOrEmpty(resolutionString), "resolution string must not be null or empty");

      Pattern pattern = Pattern.compile("^(\\d+)[xX](\\d+)$");
      Matcher matcher = pattern.matcher(resolutionString.trim());

      if (!matcher.matches()) {
         throw new IllegalArgumentException("Illegal resolution string format");
      }

      String wString = matcher.group(1);
      String hString = matcher.group(2);

      long w = parseLong(wString);
      long h = parseLong(hString);

      return new Resolution(w, h);
   }

   /**
    * Calculates and returns the <code>Ratio</code> of this resolution.
    *
    * @return the <code>Ratio</code> of this resolution.
    * @see Ratio
    */
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
