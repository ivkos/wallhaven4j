package com.ivkos.wallhaven4j.util.searchquery;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.*;

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

   private static <T> T checkNotNull(T ref, String refName)
   {
      return Preconditions.checkNotNull(ref, "%s must not be null", refName);
   }

   //region keywords

   /**
    * Sets keywords to search for.
    *
    * @param keywords A collection of keywords
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if the collection of keywords is <code>null</code>
    */
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

   /**
    * Sets keywords to search for.
    *
    * @param keywords An array or varargs of keywords
    * @return the current <code>SearchQueryBuilder</code>
    */
   public SearchQueryBuilder keywords(String... keywords)
   {
      return keywords(asList(keywords));
   }
   //endregion

   //region categories

   /**
    * Sets the categories of wallpapers to include in search result.
    *
    * @param categories A collection of categories
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if the collection of categories is <code>null</code>
    * @see Category
    */
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

   /**
    * Sets the categories of wallpapers to include in search result.
    *
    * @param categories An array or varargs of categories
    * @return the current <code>SearchQueryBuilder</code>
    * @see Category
    */
   public SearchQueryBuilder categories(Category... categories)
   {
      return categories(ImmutableSet.copyOf(categories));
   }
   //endregion

   //region purity

   /**
    * Set the purity of wallpapers to include in search result.
    *
    * @param purity Collection of purities.
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if the collection of purities is <code>null</code>
    * @see Purity
    */
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

   /**
    * Set the purity of wallpapers to include in search result.
    *
    * @param purity An array or varargs of purities.
    * @return the current <code>SearchQueryBuilder</code>
    * @see Purity
    */
   public SearchQueryBuilder purity(Purity... purity)
   {
      return purity(ImmutableSet.copyOf(purity));
   }
   //endregion

   /**
    * Sets the property that wallpapers in search result will be sorted by.
    *
    * @param sorting Sorting property.
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if sorting is <code>null</code>
    * @see Sorting
    */
   public SearchQueryBuilder sorting(Sorting sorting)
   {
      checkNotNull(sorting, "sorting");

      this.query.sorting = sorting;
      return this;
   }

   /**
    * Sets the order that wallpapers in search result will be returned.
    *
    * @param order Order. Could be either <code>Order.DESC</code>, or <code>Order.ASC</code>.
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if order is <code>null</code>
    * @see Order
    */
   public SearchQueryBuilder order(Order order)
   {
      checkNotNull(order, "order");

      this.query.order = order;
      return this;
   }

   /**
    * Sets the range of the toplist. In order for this to have effect, sorting must be set to {@link Sorting#TOPLIST}
    *
    * @param range The range of the toplist.
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if range is <code>null</code>
    * @see ToplistRange
    */
   public SearchQueryBuilder toplistRange(ToplistRange range)
   {
      checkNotNull(range, "range");

      this.query.toplistRange = range;
      return this;
   }

   //region resolutions

   /**
    * Sets the resolutions of wallpapers to include in search result.
    *
    * @param resolutions A collection of <code>Resolution</code>s
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if the collection of <code>Resolution</code>s is <code>null</code>
    * @see Resolution
    */
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

   /**
    * Sets the resolutions of wallpapers to include in search result.
    *
    * @param resolutions An array or varargs of <code>Resolution</code>s
    * @return the current <code>SearchQueryBuilder</code>
    * @see Resolution
    */
   public SearchQueryBuilder resolutions(Resolution... resolutions)
   {
      return resolutions(ImmutableSet.copyOf(resolutions));
   }
   //endregion

   //region ratios

   /**
    * Sets the ratios of wallpapers to include in search result.
    *
    * @param ratios A collection of <code>Ratio</code>s
    * @return the current <code>SearchQueryBuilder</code>
    * @throws NullPointerException if the collection of <code>Ratio</code>s is <code>null</code>
    * @see Ratio
    */
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

   /**
    * Sets the ratios of wallpapers to include in search result.
    *
    * @param ratios A collection of <code>Ratio</code>s
    * @return the current <code>SearchQueryBuilder</code>
    * @see Ratio
    */
   public SearchQueryBuilder ratios(Ratio... ratios)
   {
      return ratios(ImmutableSet.copyOf(ratios));
   }
   //endregion

   /**
    * Sets how many pages of wallpapers to fetch.
    * One page could contain 24 (default), 32 or 64 wallpapers,
    * depending on the preference of the logged in user, set in Wallhaven's settings page.
    *
    * @param pages The number of pages to fetch.
    * @return the current <code>SearchQueryBuilder</code>
    * @throws IllegalArgumentException if <code>pages</code> is not greater than zero
    */
   public SearchQueryBuilder pages(long pages)
   {
      Preconditions.checkArgument(pages > 0, "pages must be greater than zero");

      this.query.pages = pages;
      return this;
   }

   /**
    * Builds a <code>SearchQuery</code> object.
    *
    * @return a <code>SearchQuery</code> object.
    */
   public SearchQuery build()
   {
      return query;
   }
}
