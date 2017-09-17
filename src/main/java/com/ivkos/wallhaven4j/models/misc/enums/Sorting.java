package com.ivkos.wallhaven4j.models.misc.enums;

public enum Sorting
{
   RELEVANCE, RANDOM, DATE_ADDED, VIEWS, FAVORITES, TOPLIST;

   @Override
   public String toString()
   {
      return name().toLowerCase();
   }
}
