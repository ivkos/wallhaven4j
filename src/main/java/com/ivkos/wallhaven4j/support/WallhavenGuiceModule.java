package com.ivkos.wallhaven4j.support;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategoryFactory;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.support.httpclient.AbstractHttpClient;
import com.ivkos.wallhaven4j.support.httpclient.ApacheHttpClient;
import com.ivkos.wallhaven4j.support.httpclient.FileCookieStore;
import com.ivkos.wallhaven4j.support.httpclient.jsonserializer.GsonJsonSerializer;
import com.ivkos.wallhaven4j.support.httpclient.jsonserializer.JsonSerializer;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;

public class WallhavenGuiceModule extends AbstractModule
{
   private final File cookiesFile;

   public WallhavenGuiceModule()
   {
      this.cookiesFile = null;
   }

   public WallhavenGuiceModule(File cookiesFile)
   {
      this.cookiesFile = cookiesFile;
   }

   @Override
   protected void configure()
   {
      bind(AbstractHttpClient.class).to(ApacheHttpClient.class);
      bind(JsonSerializer.class).to(GsonJsonSerializer.class);

      install(new FactoryModuleBuilder().build(TagFactory.class));
      install(new FactoryModuleBuilder().build(TagCategoryFactory.class));
      install(new FactoryModuleBuilder().build(UserFactory.class));
      install(new FactoryModuleBuilder().build(WallpaperFactory.class));
      install(new FactoryModuleBuilder().build(WallpaperCollectionFactory.class));
   }

   @Provides
   @Singleton
   HttpClient provideApacheHttpClient(JsonSerializer jsonSerializer)
   {
      if (cookiesFile == null) {
         return HttpClients.createDefault();
      }

      return HttpClients.custom()
            .setDefaultCookieStore(new FileCookieStore(jsonSerializer, cookiesFile))
            .build();
   }
}
