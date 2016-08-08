package com.ivkos.wallhaven4j.models;


import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import org.joda.time.DateTime;

public class Tag extends Resource<Long>
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
   public Long getId()
   {
      return id;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_TAG + "/" + id;
   }
}
