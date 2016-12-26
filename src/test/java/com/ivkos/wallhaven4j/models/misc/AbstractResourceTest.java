package com.ivkos.wallhaven4j.models.misc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.util.WallhavenGuiceModule;
import org.junit.Test;

import static org.junit.Assert.*;

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

      assertTrue(tag1.equals(tag2));
      assertTrue(tag1.hashCode() == tag2.hashCode());
   }

   @Test
   public void inequalObjectsAreInequal()
   {
      Tag tag1 = tagFactory.create(false, 1L, "somename", Purity.SFW);
      Wallpaper wallpaper = wallpaperFactory.create(false, 1L);

      assertFalse(tag1.equals(wallpaper));
      assertFalse(tag1.hashCode() == wallpaper.hashCode());
   }

   @Test
   public void toStringDefault() throws Exception
   {
      Wallpaper wallpaper = wallpaperFactory.create(false, 42L);

      assertEquals("42", wallpaper.toString());
   }

   @Test
   public void toStringWithoutResourceType() throws Exception
   {
      Wallpaper wallpaper = wallpaperFactory.create(false, 42L);

      assertEquals("42", wallpaper.toString(false));
   }

   @Test
   public void toStringWithResourceType() throws Exception
   {
      Wallpaper wallpaper = wallpaperFactory.create(false, 42L);

      assertEquals("Wallpaper 42", wallpaper.toString(true));
   }
}
