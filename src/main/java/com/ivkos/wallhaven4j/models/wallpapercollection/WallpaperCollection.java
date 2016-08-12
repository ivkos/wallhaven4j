package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.support.AbstractResource;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.support.WallhavenSession;

import java.util.List;

public class WallpaperCollection extends AbstractResource<Long>
{
   private final long id;
   private User user;
   private String name;

   private long wallpapersCount;
   private long viewsCount;
   private long subscribersCount;
   private List<User> subscribers;

   private List<Wallpaper> wallpapers;

   @AssistedInject
   WallpaperCollection(WallhavenSession session, @Assisted long id)
   {
      super(session);
      this.id = id;
   }

   @AssistedInject
   WallpaperCollection(WallhavenSession session, @Assisted long id, @Assisted User user)
   {
      super(session);
      this.id = id;
      this.user = user;
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
