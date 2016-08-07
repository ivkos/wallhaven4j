package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;

import java.util.Objects;

public class TagCategory implements Resource
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

   public long getId()
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

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof TagCategory)) return false;
      TagCategory that = (TagCategory) o;
      return getId() == that.getId();
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(getId());
   }
}
