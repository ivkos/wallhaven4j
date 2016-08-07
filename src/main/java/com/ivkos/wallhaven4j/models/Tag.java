package com.ivkos.wallhaven4j.models;


import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import org.joda.time.DateTime;

import java.util.Objects;

public class Tag implements Resource
{
   private final long id;
   private final String name;
   private TagCategory category;
   private String[] aliases;

   private long taggedWallpapersCount;
   private long taggedWallpapersViewCount;
   private long subscribersCount;

   private User user;
   private DateTime dateCreated;

   public Tag(long id, String name)
   {
      this.id = id;
      this.name = name;
   }

   @Override
   public String toString()
   {
      return name;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_TAG + "/" + id;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof Tag)) return false;
      Tag tag = (Tag) o;
      return id == tag.id;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(id);
   }
}
