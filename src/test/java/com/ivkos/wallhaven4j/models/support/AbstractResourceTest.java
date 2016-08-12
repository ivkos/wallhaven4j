package com.ivkos.wallhaven4j.models.support;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.support.WallhavenGuiceModule;
import org.junit.Assert;
import org.junit.Test;

public class AbstractResourceTest
{
   private final Injector injector = Guice.createInjector(new WallhavenGuiceModule());
   private final TagFactory tagFactory = injector.getInstance(TagFactory.class);
   private final WallpaperCollectionFactory wallpaperCollectionFactory = injector.getInstance(WallpaperCollectionFactory.class);

   @Test
   public void equalObjectsAreEqual()
   {
      Tag tag1 = tagFactory.create(1L, "somename");
      Tag tag2 = tagFactory.create(1L, "somename");

      Assert.assertTrue(tag1.equals(tag2));
      Assert.assertTrue(tag1.hashCode() == tag2.hashCode());
   }

   @Test
   public void inequalObjectsAreInequal()
   {
      Tag tag1 = tagFactory.create(1L, "somename");
      WallpaperCollection wallpaperCollection = wallpaperCollectionFactory.create(1L);

      Assert.assertFalse(tag1.equals(wallpaperCollection));
      Assert.assertFalse(tag1.hashCode() == wallpaperCollection.hashCode());
   }
}
