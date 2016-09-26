package com.ivkos.wallhaven4j.models.wallpaper;

import com.ivkos.wallhaven4j.AbstractWallhavenTest;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WallpaperTest extends AbstractWallhavenTest
{
   private static Wallpaper w;

   @BeforeClass
   public static void setUp() throws Exception
   {
      w = getWallhaven().getWallpaper(254637);
      assertNotNull(w);
   }

   @Test
   public void getResolution() throws Exception
   {
      assertNotNull(w.getResolution());
   }

   @Test
   public void getColors() throws Exception
   {
      assertNotNull(w.getColors());
      assertFalse(w.getColors().isEmpty());
   }

   @Test
   public void getTags() throws Exception
   {
      assertNotNull(w.getTags());
      assertFalse(w.getTags().isEmpty());
   }

   @Test
   public void getPurity() throws Exception
   {
      assertEquals(Purity.SFW, w.getPurity());
   }

   @Test
   public void getUser() throws Exception
   {
      assertNotNull(w.getUser());
      assertEquals("JB00GIE", w.getUser().getId());
   }

   @Test
   public void getDateCreated() throws Exception
   {
      assertNotNull(w.getDateCreated());
   }

   @Test
   public void getCategory() throws Exception
   {
      assertEquals(Category.GENERAL, w.getCategory());
   }

   @Test
   public void getSize() throws Exception
   {
      assertTrue(w.getSize() > 0);
   }

   @Test
   public void getViewsCount() throws Exception
   {
      assertTrue(w.getViewsCount() > 0);
   }

   @Test
   public void getFavoritesCount() throws Exception
   {
      assertTrue(w.getFavoritesCount() > 0);
   }

   @Test
   public void getCollections() throws Exception
   {
      List<WallpaperCollection> collections = w.getCollections();

      assertNotNull(collections);
      assertFalse(collections.isEmpty());
   }
}
