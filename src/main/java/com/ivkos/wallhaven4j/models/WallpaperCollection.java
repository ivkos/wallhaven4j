package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.support.Resource;

import java.util.List;

public class WallpaperCollection extends Resource<Long>
{
   private final long id;
   private final User user;
   private final String name;

   private long wallpapersCount;
   private long viewsCount;
   private long subscribersCount;
   private List<User> subscribers;

   private List<Wallpaper> wallpapers;

   public WallpaperCollection(long id, User user, String name)
   {
      this.id = id;
      this.user = user;
      this.name = name;
   }

   @Override
   public Long getId()
   {
      return id;
   }

   @Override
   public String getUrl()
   {
      return user.getUrl() + "/favorites/" + id;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
