package com.ivkos.wallhaven4j;

import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WallhavenTest extends WallhavenTestBase
{
   @Test
   public void getWallpaper() throws Exception
   {
      Wallpaper w1 = getWallhaven().getWallpaper(254637);
      Wallpaper w2 = getWallhaven(true).getWallpaper(254637);

      Assert.assertNotNull(w1);
      Assert.assertNotNull(w2);
   }

   @Test(expected = ResourceNotFoundException.class)
   public void getNonExistentWallpaper() throws Exception
   {
      getWallhaven().getWallpaper(1);
   }

   @Test(expected = ResourceNotAccessibleException.class)
   public void getNsfwWallpaperWhenNotLoggedIn() throws Exception
   {
      getWallhaven().getWallpaper(8273);
   }

   @Test
   public void getCollections() throws Exception
   {
      Wallpaper w1 = getWallhaven().getWallpaper(313179);
      List<WallpaperCollection> collections = w1.getCollections();

      Assert.assertNotNull(collections);
      Assert.assertFalse(collections.isEmpty());
   }
}
