package com.ivkos.wallhaven4j.util.searchquery;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.*;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static com.ivkos.wallhaven4j.models.misc.enums.util.BitfieldSum.asThreeBitBinaryString;
import static com.ivkos.wallhaven4j.util.searchquery.SearchQueryDefaults.*;

public class SearchQuery
{
   protected String keywords = DEFAULT_KEYWORDS;
   protected EnumSet<Category> categories = DEFAULT_CATEGORIES;
   protected EnumSet<Purity> purity = DEFAULT_PURITY;
   protected Sorting sorting = DEFAULT_SORTING;
   protected Order order = DEFAULT_ORDER;
   protected ToplistRange toplistRange = DEFAULT_TOPLIST_RANGE;
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

   public ToplistRange getToplistRange()
   {
      return toplistRange;
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
      return ImmutableMap.<String, String>builder()
            .put("q", keywords)
            .put("categories", asThreeBitBinaryString(categories))
            .put("purity", asThreeBitBinaryString(purity))
            .put("resolutions", joinRatioSet(resolutions))
            .put("ratios", joinRatioSet(ratios))
            .put("sorting", sorting.toString())
            .put("order", order.toString())
            .put("topRange", toplistRange.toString())
            .build();
   }

   public String getUrl()
   {
      URIBuilder builder;
      try {
         builder = new URIBuilder(UrlPrefixes.URL_SEARCH);
      } catch (URISyntaxException e) {
         throw new RuntimeException(e);
      }

      for (Map.Entry<String, String> entry : getQueryParamsMap().entrySet()) {
         builder.addParameter(entry.getKey(), entry.getValue());
      }

      return builder.toString();
   }

   protected static <T extends Ratio> String joinRatioSet(Set<T> ratios)
   {
      return Joiner.on(",").join(ratios);
   }
}
