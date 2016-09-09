package com.ivkos.wallhaven4j.util.searchquery;

import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.*;

import java.util.EnumSet;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.copyOf;
import static com.ivkos.wallhaven4j.models.misc.enums.Category.*;
import static com.ivkos.wallhaven4j.models.misc.enums.Order.DESC;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Sorting.RELEVANCE;
import static java.util.Collections.emptySet;
import static java.util.EnumSet.of;

public class SearchQuery
{
   protected String keywords = "";
   protected EnumSet<Category> categories = of(GENERAL, ANIME, PEOPLE);
   protected EnumSet<Purity> purity = of(SFW);
   protected Sorting sorting = RELEVANCE;
   protected Order order = DESC;
   protected Set<Resolution> resolutions = emptySet();
   protected Set<Ratio> ratios = emptySet();
   protected long pages = 1;

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
