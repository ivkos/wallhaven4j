package com.ivkos.wallhaven4j.util.searchquery;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;

import java.util.Collection;
import java.util.EnumSet;

import static com.ivkos.wallhaven4j.util.searchquery.SearchQueryDefaults.*;
import static java.util.Arrays.asList;

public class SearchQueryBuilder
{
   private final SearchQuery query = new SearchQuery();

   public SearchQueryBuilder()
   {
   }

   // TODO: Add documentation

   private static <T> T checkNotNull(T ref, String refName)
   {
      return Preconditions.checkNotNull(ref, "%s must not be null", refName);
   }

   public SearchQueryBuilder keywords(Collection<String> keywords)
   {
      checkNotNull(keywords, "keywords");

      if (keywords.isEmpty()) {
         this.query.keywords = DEFAULT_KEYWORDS;
         return this;
      }

      Joiner joiner = Joiner.on(" ");
      this.query.keywords = joiner.join(keywords);
      return this;
   }

   public SearchQueryBuilder keywords(String... keywords)
   {
      return keywords(asList(keywords));
   }

   public SearchQueryBuilder categories(Collection<Category> categories)
   {
      checkNotNull(categories, "categories");

      if (categories.isEmpty()) {
         this.query.categories = DEFAULT_CATEGORIES;
         return this;
      }

      this.query.categories = EnumSet.copyOf(categories);
      return this;
   }

   public SearchQueryBuilder categories(Category... categories)
   {
      return categories(ImmutableSet.copyOf(categories));
   }

   public SearchQueryBuilder purity(Collection<Purity> purity)
   {
      checkNotNull(purity, "purity");

      if (purity.isEmpty()) {
         this.query.purity = DEFAULT_PURITY;
         return this;
      }

      this.query.purity = EnumSet.copyOf(purity);
      return this;
   }

   public SearchQueryBuilder purity(Purity... purity)
   {
      return purity(ImmutableSet.copyOf(purity));
   }

   public SearchQueryBuilder sorting(Sorting sorting)
   {
      checkNotNull(sorting, "sorting");

      this.query.sorting = sorting;
      return this;
   }

   public SearchQueryBuilder order(Order order)
   {
      checkNotNull(order, "order");

      this.query.order = order;
      return this;
   }

   public SearchQueryBuilder resolutions(Collection<Resolution> resolutions)
   {
      checkNotNull(resolutions, "resolutions");

      if (resolutions.isEmpty()) {
         this.query.resolutions = DEFAULT_RESOLUTIONS;
         return this;
      }

      this.query.resolutions = ImmutableSet.copyOf(resolutions);
      return this;
   }

   public SearchQueryBuilder resolutions(Resolution... resolutions)
   {
      return resolutions(ImmutableSet.copyOf(resolutions));
   }

   public SearchQueryBuilder ratios(Collection<Ratio> ratios)
   {
      checkNotNull(ratios, "ratios");

      if (ratios.isEmpty()) {
         this.query.ratios = DEFAULT_RATIOS;
         return this;
      }

      this.query.ratios = ImmutableSet.copyOf(ratios);
      return this;
   }

   public SearchQueryBuilder ratios(Ratio... ratios)
   {
      return ratios(ImmutableSet.copyOf(ratios));
   }

   public SearchQueryBuilder pages(long pages)
   {
      Preconditions.checkArgument(pages > 0, "pages must be greater than zero");

      this.query.pages = pages;
      return this;
   }

   public SearchQuery build()
   {
      return query;
   }
}
