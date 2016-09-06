package com.ivkos.wallhaven4j.models.misc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.util.WallhavenGuiceModule;
import org.junit.Assert;
import org.junit.Test;

public class AbstractResourceTest
{
   private final Injector injector = Guice.createInjector(new WallhavenGuiceModule());
   private final TagFactory tagFactory = injector.getInstance(TagFactory.class);
   private final WallpaperFactory wallpaperFactory = injector.getInstance(WallpaperFactory.class);

   @Test
   public void equalObjectsAreEqual()
   {
      Tag tag1 = tagFactory.create(false, 1L, "somename", Purity.SFW);
      Tag tag2 = tagFactory.create(false, 1L, "somename", Purity.SFW);

      Assert.assertTrue(tag1.equals(tag2));
      Assert.assertTrue(tag1.hashCode() == tag2.hashCode());
   }

   @Test
   public void inequalObjectsAreInequal()
   {
      Tag tag1 = tagFactory.create(false, 1L, "somename", Purity.SFW);
      Wallpaper wallpaper = wallpaperFactory.create(false, 1L);

      Assert.assertFalse(tag1.equals(wallpaper));
      Assert.assertFalse(tag1.hashCode() == wallpaper.hashCode());
   }
}
