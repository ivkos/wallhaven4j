package com.ivkos.wallhaven4j;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.util.WallhavenGuiceModule;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;

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

      session.login(username, password);
   }

   public Wallhaven()
   {
      this(null);
   }

   public Wallhaven(String username, String password)
   {
      this(username, password, null);
   }

   public User getCurrentUser()
   {
      return session.getCurrentUser();
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

   public Tag getTag(long id)
   {
      return injector.getInstance(TagFactory.class).create(true, id);
   }
}
