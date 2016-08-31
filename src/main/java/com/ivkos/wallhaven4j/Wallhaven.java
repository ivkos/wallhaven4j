package com.ivkos.wallhaven4j;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.support.WallhavenGuiceModule;
import com.ivkos.wallhaven4j.support.WallhavenSession;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotFoundException;

import java.io.File;

public class Wallhaven
{
   private final Injector injector;
   private final WallhavenSession session;

   public Wallhaven(File cookiesFile)
   {
      injector = Guice.createInjector(new WallhavenGuiceModule(cookiesFile));
      session = injector.getInstance(WallhavenSession.class);
   }

   public Wallhaven(String username, String password, File cookiesFile)
   {
      injector = Guice.createInjector(new WallhavenGuiceModule(cookiesFile));
      session = injector.getInstance(WallhavenSession.class);
   }

   public Wallhaven()
   {
      this(null);
   }

   public Wallhaven(String username, String password)
   {
      this(username, password, null);
   }

   /**
    * @param id Wallpaper's id
    * @return the wallpaper
    * @throws ResourceNotFoundException if there's wallpaper with the set id
    */
   public Wallpaper getWallpaper(long id)
   {
      Preconditions.checkArgument(id > 0);

      return injector.getInstance(WallpaperFactory.class).create(true, id);
   }
}
