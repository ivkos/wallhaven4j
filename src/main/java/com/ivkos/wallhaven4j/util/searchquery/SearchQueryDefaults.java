package com.ivkos.wallhaven4j.util.searchquery;

import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.*;

import java.util.EnumSet;
import java.util.Set;

import static com.ivkos.wallhaven4j.models.misc.enums.Category.*;
import static com.ivkos.wallhaven4j.models.misc.enums.Order.DESC;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Sorting.RELEVANCE;
import static com.ivkos.wallhaven4j.models.misc.enums.ToplistRange.LAST_1_MONTH;
import static java.util.Collections.emptySet;

public abstract class SearchQueryDefaults
{
   public static final String DEFAULT_KEYWORDS = "";
   public static final EnumSet<Category> DEFAULT_CATEGORIES = EnumSet.of(GENERAL, ANIME, PEOPLE);
   public static final EnumSet<Purity> DEFAULT_PURITY = EnumSet.of(SFW);
   public static final Sorting DEFAULT_SORTING = RELEVANCE;
   public static final Order DEFAULT_ORDER = DESC;
   public static final ToplistRange DEFAULT_TOPLIST_RANGE = LAST_1_MONTH;
   public static final Set<Resolution> DEFAULT_RESOLUTIONS = emptySet();
   public static final Set<Ratio> DEFAULT_RATIOS = emptySet();
   public static final long DEFAULT_PAGES = 1;
}
