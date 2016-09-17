package com.ivkos.wallhaven4j.util.searchquery;

import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;

import java.util.EnumSet;
import java.util.Set;

import static com.ivkos.wallhaven4j.models.misc.enums.Category.*;
import static com.ivkos.wallhaven4j.models.misc.enums.Order.DESC;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Sorting.RELEVANCE;
import static java.util.Collections.emptySet;

final class SearchQueryDefaults
{
   static final String DEFAULT_KEYWORDS = "";
   static final EnumSet<Category> DEFAULT_CATEGORIES = EnumSet.of(GENERAL, ANIME, PEOPLE);
   static final EnumSet<Purity> DEFAULT_PURITY = EnumSet.of(SFW);
   static final Sorting DEFAULT_SORTING = RELEVANCE;
   static final Order DEFAULT_ORDER = DESC;
   static final Set<Resolution> DEFAULT_RESOLUTIONS = emptySet();
   static final Set<Ratio> DEFAULT_RATIOS = emptySet();
   static final long DEFAULT_PAGES = 1;

   private SearchQueryDefaults()
   {
   }
}
