package com.ivkos.wallhaven4j.util.searchquery;

import com.google.common.base.Joiner;
import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.ivkos.wallhaven4j.models.misc.enums.util.BitfieldSum.asThreeBitBinaryString;
import static com.ivkos.wallhaven4j.util.searchquery.SearchQueryDefaults.*;
import static java.util.Collections.unmodifiableMap;

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

   public Map<String, String> getQueryParamsMap()
   {
      Map<String, String> map = new HashMap<>();

      if (!DEFAULT_KEYWORDS.equals(keywords)) map.put("q", keywords);
      if (!DEFAULT_CATEGORIES.equals(categories)) map.put("categories", asThreeBitBinaryString(categories));
      if (!DEFAULT_PURITY.equals(purity)) map.put("purity", asThreeBitBinaryString(purity));
      if (!DEFAULT_RESOLUTIONS.equals(resolutions)) map.put("resolution", joinRatioSet(resolutions));
      if (!DEFAULT_RATIOS.equals(ratios)) map.put("ratios", joinRatioSet(ratios));
      if (!DEFAULT_SORTING.equals(sorting)) map.put("sorting", sorting.toString());
      if (!DEFAULT_ORDER.equals(order)) map.put("order", order.toString());

      return unmodifiableMap(map);
   }

   protected <T extends Ratio> String joinRatioSet(Set<T> ratios)
   {
      return Joiner.on(",").join(ratios);
   }
}
