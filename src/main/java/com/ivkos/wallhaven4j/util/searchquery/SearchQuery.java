package com.ivkos.wallhaven4j.util.searchquery;

import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.*;

import java.util.EnumSet;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.copyOf;
import static com.ivkos.wallhaven4j.util.searchquery.SearchQueryDefaults.*;

public class SearchQuery
{
   protected String keywords = DEFAULT_KEYWORDS;
   protected EnumSet<Category> categories = DEFAULT_CATEGORIES;
   protected EnumSet<Purity> purity = DEFAULT_PURITY;
   protected Sorting sorting = DEFAULT_SORTING;
   protected Order order = DEFAULT_ORDER;
   protected Set<Resolution> resolutions = DEFAULT_RESOLUTIONS;
   protected Set<Ratio> ratios = DEFAULT_RATIOS;
   protected long pages = DEFAULT_PAGES;

   protected SearchQuery()
   {
   }

   @SafeVarargs
   protected static long bitfieldSumOf(Enum<? extends BitfieldCompatible>... enums)
   {
      return bitfieldSumOf(copyOf(enums));
   }

   protected static long bitfieldSumOf(Iterable<Enum<? extends BitfieldCompatible>> enums)
   {
      long result = 0;

      for (Enum<? extends BitfieldCompatible> theEnum : enums) {
         BitfieldCompatible bfc = (BitfieldCompatible) theEnum;

         result += bfc.getBitfieldValue();
      }

      return result;
   }

   //region getters
   public String getKeywords()
   {
      return keywords;
   }

   public EnumSet<Category> getCategories()
   {
      return categories;
   }

   public EnumSet<Purity> getPurity()
   {
      return purity;
   }

   public Sorting getSorting()
   {
      return sorting;
   }

   public Order getOrder()
   {
      return order;
   }

   public Set<Resolution> getResolutions()
   {
      return resolutions;
   }

   public Set<Ratio> getRatios()
   {
      return ratios;
   }

   public long getPages()
   {
      return pages;
   }
   //endregion
}
