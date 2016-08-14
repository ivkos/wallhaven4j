package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.support.AbstractResource;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.support.WallhavenSession;

import java.util.List;

public class WallpaperCollection extends AbstractResource<WallpaperCollectionIdentifier>
{
   private String name;

   private long wallpapersCount;
   private long viewsCount;
   private long subscribersCount;
   private List<User> subscribers;

   private List<Wallpaper> wallpapers;

   @AssistedInject
   protected WallpaperCollection(WallhavenSession session, @Assisted boolean preloadDom, @Assisted WallpaperCollectionIdentifier id)
   {
      super(session, preloadDom, id);
   }

   @AssistedInject
   protected WallpaperCollection(WallhavenSession session, @Assisted boolean preloadDom, @Assisted WallpaperCollectionIdentifier id, @Assisted String name)
   {
      super(session, preloadDom, id);
      this.name = name;
   }

   public User getUser()
   {
      return getId().getUser();
   }

   @Override
   public String getUrl()
   {
      return getUser().getUrl() + "/favorites/" + id.getLongId();
   }

   @Override
   public String toString()
   {
      return name;
   }
}
