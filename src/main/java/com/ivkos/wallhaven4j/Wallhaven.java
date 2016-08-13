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
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

   /**
    * @return The current user, or null if this is an anonymous session
    */
   @Nullable
   public User getCurrentUser()
   {
      return session.getUsername() == null ? null
            : injector.getInstance(UserFactory.class).create(false, session.getUsername());
   }

   /**
    * @param id Wallpaper's id
    * @return the wallpaper
    * @throws ResourceNotFoundException if there's wallpaper with the set id
    */
   public @NotNull Wallpaper getWallpaper(long id)
   {
      Preconditions.checkArgument(id > 0);

      return injector.getInstance(WallpaperFactory.class).create(true, id);
   }
}
