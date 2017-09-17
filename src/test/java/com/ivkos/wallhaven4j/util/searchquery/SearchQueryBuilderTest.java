package com.ivkos.wallhaven4j.util.searchquery;

import com.google.common.collect.ImmutableSet;
import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import static com.ivkos.wallhaven4j.models.misc.enums.Category.GENERAL;
import static com.ivkos.wallhaven4j.models.misc.enums.Category.PEOPLE;
import static com.ivkos.wallhaven4j.models.misc.enums.Order.DESC;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SKETCHY;
import static com.ivkos.wallhaven4j.models.misc.enums.Sorting.DATE_ADDED;
import static com.ivkos.wallhaven4j.models.misc.enums.ToplistRange.LAST_1_DAY;
import static com.ivkos.wallhaven4j.util.searchquery.SearchQuery.joinRatioSet;
import static com.ivkos.wallhaven4j.util.searchquery.SearchQueryDefaults.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class SearchQueryBuilderTest
{
   private SearchQueryBuilder sqb;

   @Before
   public void setUp() throws Exception
   {
      sqb = new SearchQueryBuilder();
   }

   @Test
   public void keywordsAreJoinedCorrectly() throws Exception
   {
      sqb.keywords("kw1");
      assertEquals("kw1", sqb.build().getKeywords());

      sqb.keywords("kw1 kw2");
      assertEquals("kw1 kw2", sqb.build().getKeywords());

      sqb.keywords("kw1", "kw2");
      assertEquals("kw1 kw2", sqb.build().getKeywords());

      sqb.keywords(asList("kw1", "kw2"));
      assertEquals("kw1 kw2", sqb.build().getKeywords());
   }

   @Test(expected = NullPointerException.class)
   public void keywordsWontAcceptNull() throws Exception
   {
      sqb.keywords((Collection<String>) null);
   }

   @Test
   public void defaultKeywords() throws Exception
   {
      sqb.keywords();
      assertEquals(DEFAULT_KEYWORDS, sqb.build().getKeywords());
   }

   @Test
   public void categories() throws Exception
   {
      sqb.categories();
      assertTrue(!sqb.build().getCategories().isEmpty()); // should contain the default categories

      sqb.categories(GENERAL);
      assertTrue(sqb.build().getCategories().contains(GENERAL));

      sqb.categories(GENERAL, PEOPLE);
      EnumSet<Category> categories = sqb.build().getCategories();
      assertTrue(categories.contains(GENERAL));
      assertTrue(categories.contains(PEOPLE));
   }

   @Test(expected = NullPointerException.class)
   public void categoriesWontAcceptNull() throws Exception
   {
      sqb.categories(((Collection<Category>) null));
   }

   @Test
   public void defaultCategories() throws Exception
   {
      sqb.categories();
      assertEquals(DEFAULT_CATEGORIES, sqb.build().getCategories());
   }

   @Test
   public void purity() throws Exception
   {
      sqb.purity(SFW);
      assertEquals(EnumSet.of(SFW), sqb.build().getPurity());

      sqb.purity(SFW, SKETCHY);
      assertEquals(EnumSet.of(SFW, SKETCHY), sqb.build().getPurity());

      sqb.purity(EnumSet.of(SFW));
      assertEquals(EnumSet.of(SFW), sqb.build().getPurity());

      sqb.purity(EnumSet.of(SFW, SKETCHY));
      assertEquals(EnumSet.of(SFW, SKETCHY), sqb.build().getPurity());
   }

   @Test(expected = NullPointerException.class)
   public void purityWontAcceptNull() throws Exception
   {
      sqb.purity(((Collection<Purity>) null));
   }

   @Test
   public void defaultPurity() throws Exception
   {
      sqb.purity();
      assertEquals(DEFAULT_PURITY, sqb.build().getPurity());
   }

   @Test
   public void sorting() throws Exception
   {
      sqb.sorting(DATE_ADDED);
      assertEquals(DATE_ADDED, sqb.build().getSorting());
   }

   @Test(expected = NullPointerException.class)
   public void sortingWontAcceptNull() throws Exception
   {
      sqb.sorting(null);
   }

   @Test
   public void order() throws Exception
   {
      sqb.order(DESC);
      assertEquals(DESC, sqb.build().getOrder());
   }

   @Test(expected = NullPointerException.class)
   public void orderWontAcceptNull() throws Exception
   {
      sqb.order(null);
   }

   @Test
   public void toplistRange() throws Exception
   {
      sqb.toplistRange(LAST_1_DAY);
      assertEquals(LAST_1_DAY, sqb.build().getToplistRange());
   }

   @Test(expected = NullPointerException.class)
   public void toplsitRangeWontAcceptNull() throws Exception
   {
      sqb.toplistRange(null);
   }

   @Test
   public void resolutions() throws Exception
   {
      Resolution r1080p = new Resolution(1920, 1080);
      Resolution r720p = new Resolution(1280, 720);

      sqb.resolutions(r1080p);
      assertEquals(ImmutableSet.of(r1080p), sqb.build().getResolutions());

      sqb.resolutions(Collections.singletonList(r1080p));
      assertEquals(ImmutableSet.of(r1080p), sqb.build().getResolutions());

      sqb.resolutions(r1080p, r720p);
      assertEquals(ImmutableSet.of(r1080p, r720p), sqb.build().getResolutions());

      sqb.resolutions(asList(r1080p, r720p));
      assertEquals(ImmutableSet.of(r1080p, r720p), sqb.build().getResolutions());
   }

   @Test(expected = NullPointerException.class)
   public void resolutionsWontAcceptNull() throws Exception
   {
      sqb.resolutions(((Collection<Resolution>) null));
   }

   @Test
   public void defaultResolutions() throws Exception
   {
      sqb.resolutions();
      assertEquals(DEFAULT_RESOLUTIONS, sqb.build().getResolutions());
   }

   @Test
   public void ratios() throws Exception
   {
      Resolution r16x9 = new Resolution(16, 9);
      Resolution r4x3 = new Resolution(4, 3);

      sqb.ratios(r16x9);
      assertEquals(ImmutableSet.of(r16x9), sqb.build().getRatios());

      sqb.ratios(Collections.singletonList(r16x9));
      assertEquals(ImmutableSet.of(r16x9), sqb.build().getRatios());

      sqb.ratios(r16x9, r4x3);
      assertEquals(ImmutableSet.of(r16x9, r4x3), sqb.build().getRatios());

      sqb.ratios(asList(r16x9, r4x3));
      assertEquals(ImmutableSet.of(r16x9, r4x3), sqb.build().getRatios());
   }

   @Test(expected = NullPointerException.class)
   public void ratiosWontAcceptNull() throws Exception
   {
      sqb.ratios(((Collection<Ratio>) null));
   }

   @Test
   public void defaultRatios() throws Exception
   {
      sqb.ratios();
      assertEquals(DEFAULT_RATIOS, sqb.build().getRatios());
   }

   @Test
   public void pages() throws Exception
   {
      sqb.pages(7);
      assertEquals(7, sqb.build().getPages());
   }

   @Test(expected = IllegalArgumentException.class)
   public void pagesWontAcceptZero() throws Exception
   {
      sqb.pages(0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void pagesWontAcceptNegatives() throws Exception
   {
      sqb.pages(-1);
   }

   @Test
   public void build() throws Exception
   {
      assertNotNull(sqb.build());
   }

   @Test
   public void joinRatios() throws Exception
   {
      String s1 = joinRatioSet(ImmutableSet.of(new Ratio(16, 9), new Resolution(1920, 1080)));
      assertEquals("16x9,1920x1080", s1);

      String s2 = joinRatioSet(ImmutableSet.of(new Ratio(16, 9)));
      assertEquals("16x9", s2);

      String s3 = joinRatioSet(Collections.emptySet());
      assertEquals("", s3);
   }

   @Test
   public void getUrl() throws Exception
   {
      String url = sqb.build().getUrl();
      assertEquals("https://alpha.wallhaven.cc/search?q=&categories=111&purity=100&resolutions=&ratios=" +
            "&sorting=relevance&order=desc&topRange=1M", url);

      String url2 = sqb.keywords("cats").build().getUrl();
      assertEquals("https://alpha.wallhaven.cc/search?q=cats&categories=111&purity=100&resolutions=&ratios=" +
            "&sorting=relevance&order=desc&topRange=1M", url2);
   }
}
