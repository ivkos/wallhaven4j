package com.ivkos.wallhaven4j;

import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WallhavenTest
{
   private final Wallhaven whAnon = new Wallhaven();
   private final Wallhaven whLoggedIn = new Wallhaven(
         System.getenv("WALLHAVEN_USERNAME"),
         System.getenv("WALLHAVEN_PASSWORD")
   );

   @Test
   public void getCurrentUser() throws Exception
   {
      Assert.assertEquals(null, whAnon.getCurrentUser());
      Assert.assertNotNull(whLoggedIn.getCurrentUser());
   }

   @Test
   public void getWallpaper() throws Exception
   {
      Wallpaper w1 = whAnon.getWallpaper(254637);
      Wallpaper w2 = whLoggedIn.getWallpaper(254637);

      Assert.assertNotNull(w1);
      Assert.assertNotNull(w2);
   }

   @Test(expected = ResourceNotFoundException.class)
   public void getNonExistentWallpaper() throws Exception
   {
      whAnon.getWallpaper(1);
   }

   @Test(expected = ResourceNotAccessibleException.class)
   public void getNsfwWallpaperWhenNotLoggedIn() throws Exception
   {
      whAnon.getWallpaper(8273);
   }

   @Test
   public void getCollections() throws Exception
   {
      Wallpaper w1 = whAnon.getWallpaper(103929);
      List<WallpaperCollection> collections = w1.getCollections();

      Assert.assertNotNull(collections);
      Assert.assertFalse(collections.isEmpty());
   }
}
