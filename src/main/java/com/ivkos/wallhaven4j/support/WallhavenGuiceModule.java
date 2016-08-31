package com.ivkos.wallhaven4j.support;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategoryFactory;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.support.httpclient.filecookiestore.FileCookieStoreFactory;
import com.ivkos.wallhaven4j.support.httpclient.jsonserializer.GsonJsonSerializer;
import com.ivkos.wallhaven4j.support.httpclient.jsonserializer.JsonSerializer;

public class WallhavenGuiceModule extends AbstractModule
{
   private final WallhavenSession session;

   public WallhavenGuiceModule()
   {
      session = new WallhavenSession();
   }

   public WallhavenGuiceModule(String username, String password)
   {
      session = new WallhavenSession(username, password);
   }

   @Override
   protected void configure()
   {
      bind(WallhavenSession.class).toInstance(session);
      bind(JsonSerializer.class).to(GsonJsonSerializer.class);

      install(new FactoryModuleBuilder().build(FileCookieStoreFactory.class));

      install(new FactoryModuleBuilder().build(TagFactory.class));
      install(new FactoryModuleBuilder().build(TagCategoryFactory.class));
      install(new FactoryModuleBuilder().build(UserFactory.class));
      install(new FactoryModuleBuilder().build(WallpaperFactory.class));
      install(new FactoryModuleBuilder().build(WallpaperCollectionFactory.class));
   }
}
