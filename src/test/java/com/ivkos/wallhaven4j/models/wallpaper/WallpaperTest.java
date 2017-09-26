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
   private static long WALLPAPER_ID_SFW = 254637;
   private static long WALLPAPER_ID_SKETCHY = 553;
   private static long WALLPAPER_ID_NSFW = 271712;

   private static Wallpaper wSfw, wSketchy, wNsfw;

   @BeforeClass
   public static void setUp() throws Exception
   {
      wSfw = getWallhaven().getWallpaper(WALLPAPER_ID_SFW);
      assertNotNull(wSfw);

      wSketchy = getWallhaven().getWallpaper(WALLPAPER_ID_SKETCHY);
      assertNotNull(wSketchy);

      wNsfw = getWallhaven(true).getWallpaper(WALLPAPER_ID_NSFW);
      assertNotNull(wNsfw);
   }

   @Test
   public void getShortLink() throws Exception
   {
      assertEquals("https://whvn.cc/" + WALLPAPER_ID_SFW, wSfw.getShortLink());
   }

   @Test
   public void getResolution() throws Exception
   {
      assertNotNull(wSfw.getResolution());
      assertNotNull(wSketchy.getResolution());
      assertNotNull(wNsfw.getResolution());
   }

   @Test
   public void getColors() throws Exception
   {
      assertNotNull(wSfw.getColors());
      assertFalse(wSfw.getColors().isEmpty());

      assertNotNull(wSketchy.getColors());
      assertFalse(wSketchy.getColors().isEmpty());

      assertNotNull(wNsfw.getColors());
      assertFalse(wNsfw.getColors().isEmpty());
   }

   @Test
   public void getTags() throws Exception
   {
      assertNotNull(wSfw.getTags());
      assertFalse(wSfw.getTags().isEmpty());

      assertNotNull(wSketchy.getTags());
      assertFalse(wSketchy.getTags().isEmpty());

      assertNotNull(wNsfw.getTags());
      assertFalse(wNsfw.getTags().isEmpty());
   }

   @Test
   public void getPurity() throws Exception
   {
      assertEquals(Purity.SFW, wSfw.getPurity());
      assertEquals(Purity.SKETCHY, wSketchy.getPurity());
      assertEquals(Purity.NSFW, wNsfw.getPurity());
   }

   @Test
   public void getUser() throws Exception
   {
      assertNotNull(wSfw.getUser());
      assertEquals("JB00GIE", wSfw.getUser().getId());

      assertNotNull(wSketchy.getUser());
      assertNotNull(wNsfw.getUser());
   }

   @Test
   public void getDateCreated() throws Exception
   {
      assertNotNull(wSfw.getDateCreated());
      assertNotNull(wSketchy.getDateCreated());
      assertNotNull(wNsfw.getDateCreated());
   }

   @Test
   public void getCategory() throws Exception
   {
      assertEquals(Category.GENERAL, wSfw.getCategory());
      assertEquals(Category.PEOPLE, wSketchy.getCategory());
      assertEquals(Category.PEOPLE, wNsfw.getCategory());
   }

   @Test
   public void getSize() throws Exception
   {
      assertTrue(wSfw.getSize() > 0);
      assertTrue(wSketchy.getSize() > 0);
      assertTrue(wNsfw.getSize() > 0);
   }

   @Test
   public void getViewsCount() throws Exception
   {
      assertTrue(wSfw.getViewsCount() > 0);
      assertTrue(wSketchy.getViewsCount() > 0);
      assertTrue(wNsfw.getViewsCount() > 0);
   }

   @Test
   public void getFavoritesCount() throws Exception
   {
      assertTrue(wSfw.getFavoritesCount() > 0);
      assertTrue(wSketchy.getFavoritesCount() > 0);
      assertTrue(wNsfw.getFavoritesCount() > 0);
   }

   @Test
   public void getCollections() throws Exception
   {
      List<WallpaperCollection> collectionsSfw = wSfw.getCollections();
      assertNotNull(collectionsSfw);
      assertFalse(collectionsSfw.isEmpty());

      List<WallpaperCollection> collectionsSketchy = wSketchy.getCollections();
      assertNotNull(collectionsSketchy);
      assertFalse(collectionsSketchy.isEmpty());

      List<WallpaperCollection> collectionsNsfw = wNsfw.getCollections();
      assertNotNull(collectionsNsfw);
      assertFalse(collectionsNsfw.isEmpty());
   }

   @Test
   public void getImageUrl() throws Exception
   {
      String imageUrl = wSfw.getImageUrl();
      assertNotNull(imageUrl);
      assertTrue(imageUrl.endsWith(".jpg"));

      Wallpaper pngWallpaper = getWallhaven().getWallpaper(154065);
      String pngImageUrl = pngWallpaper.getImageUrl();

      assertNotNull(pngImageUrl);
      assertTrue(pngImageUrl.endsWith(".png"));
   }

   @Test
   public void getThumbnailUrl() throws Exception
   {
      String thumbnailUrl = wSfw.getThumbnailUrl();
      assertNotNull(thumbnailUrl);
      assertFalse(thumbnailUrl.isEmpty());
   }
}
