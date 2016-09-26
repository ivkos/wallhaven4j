package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.ivkos.wallhaven4j.AbstractWallhavenTest;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

public class WallpaperCollectionTest extends AbstractWallhavenTest
{
   private static WallpaperCollection wc;
   private static WallpaperCollection wcLoggedIn;

   @BeforeClass
   public static void setUp() throws Exception
   {
      wc = getWallhaven().getWallpaperCollection("Gandalf", 2);
      wcLoggedIn = getWallhaven(true).getWallpaperCollection("Gandalf", 2);
   }

   @Test
   public void getUser() throws Exception
   {
      assertEquals("Gandalf", wc.getUser().getUsername());
   }

   @Test
   public void getName() throws Exception
   {
      assertNotNull(wc.getName());
   }

   @Test
   public void getWallpapersCount() throws Exception
   {
      assertTrue(wc.getWallpapersCount() > 0);
   }

   @Test
   public void getViewsCount() throws Exception
   {
      assertTrue(wc.getViewsCount() > 0);
   }

   @Test
   public void getSubscribersCount() throws Exception
   {
      assertTrue(wc.getSubscribersCount() > 0);
   }

   @Test
   public void getSubscribers() throws Exception
   {
      List<WallpaperCollection> currentUserCollections = getWallhaven(true).getCurrentUser().getCollections();

      if (currentUserCollections.isEmpty()) return;

      List<WallpaperCollection> nonZeroCollections = newArrayList(filter(currentUserCollections,
            c -> c.getSubscribersCount() > 0));

      if (nonZeroCollections.isEmpty()) return;

      WallpaperCollection defaultCollection = nonZeroCollections.get(0);
      List<User> subscribers = defaultCollection.getSubscribers();

      assertNotNull(subscribers);
   }

   @Test(expected = ResourceNotAccessibleException.class)
   public void getSubscribersFailsForForeignCollections() throws Exception
   {
      wc.getSubscribers();
   }

   @Test(expected = ResourceNotAccessibleException.class)
   public void getSubscribersFailsForForeignCollectionsWhileLoggedIn() throws Exception
   {
      wcLoggedIn.getSubscribers();
   }

   @Test
   public void getWallpapers() throws Exception
   {
      List<Wallpaper> wallpapers = wc.getWallpapers(3, Purity.SFW, Purity.SKETCHY, Purity.NSFW);

      assertEquals(3 * 24, wallpapers.size());
   }
}
