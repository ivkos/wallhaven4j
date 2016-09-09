package com.ivkos.wallhaven4j.util.searchquery;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;

import java.util.EnumSet;

import static java.util.Arrays.asList;

public class SearchQueryBuilder
{
   private final SearchQuery query = new SearchQuery();

   public SearchQueryBuilder()
   {
   }

   // TODO: Add documentation

   public SearchQueryBuilder keywords(Iterable<String> keywords)
   {
      Joiner joiner = Joiner.on(" ");
      this.query.keywords = joiner.join(keywords);
      return this;
   }

   public SearchQueryBuilder keywords(String... keywords)
   {
      return keywords(asList(keywords));
   }

   public SearchQueryBuilder categories(Iterable<Category> categories)
   {
      this.query.categories = EnumSet.copyOf(ImmutableSet.copyOf(categories));
      return this;
   }

   public SearchQueryBuilder categories(Category... categories)
   {
      return categories(ImmutableSet.copyOf(categories));
   }

   public SearchQueryBuilder purity(Iterable<Purity> purity)
   {
      this.query.purity = EnumSet.copyOf(ImmutableSet.copyOf(purity));
      return this;
   }

   public SearchQueryBuilder purity(Purity... purity)
   {
      return purity(ImmutableSet.copyOf(purity));
   }

   public SearchQueryBuilder sorting(Sorting sorting)
   {
      this.query.sorting = sorting;
      return this;
   }

   public SearchQueryBuilder order(Order order)
   {
      this.query.order = order;
      return this;
   }

   public SearchQueryBuilder resolutions(Iterable<Resolution> resolutions)
   {
      this.query.resolutions = ImmutableSet.copyOf(resolutions);
      return this;
   }

   public SearchQueryBuilder resolutions(Resolution... resolutions)
   {
      return resolutions(ImmutableSet.copyOf(resolutions));
   }

   public SearchQueryBuilder ratios(Iterable<Ratio> ratios)
   {
      this.query.ratios = ImmutableSet.copyOf(ratios);
      return this;
   }

   public SearchQueryBuilder ratios(Ratio... ratios)
   {
      return ratios(ImmutableSet.copyOf(ratios));
   }

   public SearchQueryBuilder pages(long pages)
   {
      this.query.pages = pages;
      return this;
   }

   public SearchQuery build()
   {
      return query;
   }
}
