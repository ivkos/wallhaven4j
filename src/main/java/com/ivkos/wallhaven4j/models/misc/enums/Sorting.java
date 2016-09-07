package com.ivkos.wallhaven4j.models.misc.enums;

public enum Sorting
{
   RELEVANCE, RANDOM, DATE_ADDED, VIEWS, FAVORITES;

   public String getValue()
   {
      return this.name().toLowerCase();
   }
}
