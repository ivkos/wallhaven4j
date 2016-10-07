package com.ivkos.wallhaven4j.util;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategoryFactory;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlParser;
import com.ivkos.wallhaven4j.util.htmlparser.jsoup.JsoupHtmlParser;
import com.ivkos.wallhaven4j.util.httpclient.HttpClient;
import com.ivkos.wallhaven4j.util.httpclient.apache.ApacheHttpClient;
import com.ivkos.wallhaven4j.util.jsonserializer.GsonJsonSerializer;
import com.ivkos.wallhaven4j.util.jsonserializer.JsonSerializer;
import com.ivkos.wallhaven4j.util.pagecrawler.thumbnailpage.ThumbnailPageCrawlerFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.File;

import static com.google.inject.Scopes.SINGLETON;

public class WallhavenGuiceModule extends AbstractModule
{
   private static final Class[] factories = {
         TagFactory.class,
         TagCategoryFactory.class,
         UserFactory.class,
         WallpaperFactory.class,
         WallpaperCollectionFactory.class,

         ThumbnailPageCrawlerFactory.class
   };

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
      bind(HttpClient.class).to(ApacheHttpClient.class).in(SINGLETON);
      bind(HtmlParser.class).to(JsoupHtmlParser.class).in(SINGLETON);
      bind(JsonSerializer.class).to(GsonJsonSerializer.class).in(SINGLETON);

      for (Class factoryClass : factories) {
         install(new FactoryModuleBuilder().build(factoryClass));
      }
   }

   @Provides
   private org.apache.http.client.HttpClient provideApacheHttpClient(JsonSerializer jsonSerializer)
   {
      HttpClientBuilder builder = HttpClients.custom();

      if (cookiesFile != null) {
         builder.setDefaultCookieStore(new FileCookieStore(jsonSerializer, cookiesFile));
      }

      return builder.build();
   }
}
