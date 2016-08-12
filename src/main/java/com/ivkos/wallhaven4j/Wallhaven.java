package com.ivkos.wallhaven4j;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.support.WallhavenGuiceModule;
import com.ivkos.wallhaven4j.support.WallhavenSession;

public class Wallhaven
{
   private final Injector injector;
   private final WallhavenSession session;

   public Wallhaven()
   {
      injector = Guice.createInjector(new WallhavenGuiceModule());
      session = injector.getInstance(WallhavenSession.class);
   }

   public Wallhaven(String username, String password)
   {
      injector = Guice.createInjector(new WallhavenGuiceModule(username, password));
      session = injector.getInstance(WallhavenSession.class);
   }

   public User getCurrentUser()
   {
      return injector.getInstance(UserFactory.class).create(session.getUsername());
   }

   public Wallpaper getWallpaper(Long id)
   {
      Preconditions.checkNotNull(id);

      return injector.getInstance(WallpaperFactory.class).create(id);
   }
}
