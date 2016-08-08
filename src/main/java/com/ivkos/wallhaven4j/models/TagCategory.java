package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;

public class TagCategory extends Resource<Long>
{
   private final long id;
   private final String name;

   private TagCategory parentCategory;

   public TagCategory(long id, String name)
   {
      this.id = id;
      this.name = name;
   }

   public TagCategory(long id, String name, TagCategory parentCategory)
   {
      this.id = id;
      this.name = name;
      this.parentCategory = parentCategory;
   }

   @Override
   public Long getId()
   {
      return id;
   }

   public String getName()
   {
      return name;
   }

   public TagCategory getParentCategory()
   {
      return parentCategory;
   }

   @Override
   public String toString()
   {
      return name;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_TAGS + "/" + id;
   }
}
