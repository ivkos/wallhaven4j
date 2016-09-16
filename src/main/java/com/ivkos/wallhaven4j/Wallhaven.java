package com.ivkos.wallhaven4j;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.WallhavenGuiceModule;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;

import java.io.File;

public class Wallhaven
{
   private final Injector injector;
   private final WallhavenSession session;
   private final ResourceFactoryFactory rff;

   public Wallhaven(File cookiesFile)
   {
      injector = Guice.createInjector(new WallhavenGuiceModule(cookiesFile));
      session = injector.getInstance(WallhavenSession.class);
      rff = injector.getInstance(ResourceFactoryFactory.class);
   }

   public Wallhaven(String username, String password, File cookiesFile)
   {
      injector = Guice.createInjector(new WallhavenGuiceModule(cookiesFile));
      session = injector.getInstance(WallhavenSession.class);
      rff = injector.getInstance(ResourceFactoryFactory.class);

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

      return rff.getFactoryFor(Wallpaper.class).create(true, id);
   }

   public Tag getTag(long id)
   {
      return rff.getFactoryFor(Tag.class).create(true, id);
   }

   public User getUser(String username)
   {
      return rff.getFactoryFor(User.class).create(true, username);
   }
}
