package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.support.Resource;

import java.util.List;
import java.util.Objects;

public class WallpaperCollection implements Resource
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
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof WallpaperCollection)) return false;
      WallpaperCollection that = (WallpaperCollection) o;
      return id == that.id;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(id);
   }

   @Override
   public String getUrl()
   {
      return user.getUrl() + "/favorites/" + id;
   }
}
