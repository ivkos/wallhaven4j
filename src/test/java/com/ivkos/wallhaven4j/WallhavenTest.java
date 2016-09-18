package com.ivkos.wallhaven4j;

import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.util.exceptions.LoginException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class WallhavenTest extends AbstractWallhavenTest
{
   @Test(expected = LoginException.class)
   public void loginWithIncorrectCredentials()
   {
      new Wallhaven(getWallhavenUsername(), "thewrongpassword");
   }

   @Test(expected = ResourceNotFoundException.class)
   public void getNonExistentTag() throws Exception
   {
      getWallhaven().getTag(999999999);
   }

   @Test(expected = ResourceNotFoundException.class)
   public void getNonExistentUser() throws Exception
   {
      getWallhaven().getUser("nonexistentuser");
   }

   @Test(expected = ResourceNotFoundException.class)
   public void getNonExistentWallpaperCollection() throws Exception
   {
      getWallhaven().getWallpaperCollection("ivkos", 999999999);
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
   public void getCurrentUser() throws Exception
   {
      assertNull(getWallhaven().getCurrentUser());

      User currentUser = getWallhaven(true).getCurrentUser();
      assertNotNull(currentUser);
      assertEquals(getWallhavenUsername(), currentUser.getUsername());
   }

   @Test(expected = IllegalArgumentException.class)
   public void getTagWithZeroId() throws Exception
   {
      getWallhaven().getTag(0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getTagWithNegativeId() throws Exception
   {
      getWallhaven().getTag(-1);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getUserWithNullUsername() throws Exception
   {
      getWallhaven().getUser(null);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getUserWithEmptyUsername() throws Exception
   {
      getWallhaven().getUser("");
   }

   @Test(expected = IllegalArgumentException.class)
   public void getWallpaperWithZeroId() throws Exception
   {
      getWallhaven().getWallpaper(0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getWallpaperWithNegativeId() throws Exception
   {
      getWallhaven().getWallpaper(-1);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getWallpaperCollectionWithNullUsername() throws Exception
   {
      getWallhaven().getWallpaperCollection(null, 0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getWallpaperCollectionWithEmptyUsername() throws Exception
   {
      getWallhaven().getWallpaperCollection("", 0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getWallpaperCollectionWithZeroId() throws Exception
   {
      getWallhaven().getWallpaperCollection("someone", 0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void getWallpaperCollectionWithNegativeId() throws Exception
   {
      getWallhaven().getWallpaperCollection("someone", -1);
   }

   @Test
   public void testCookies()
   {
      File cookiesFile = new File("testcookies.json");

      Wallhaven wh1 = new Wallhaven(
            getWallhavenUsername(),
            getWallhavenPassword(),
            cookiesFile
      );

      assertNotNull(wh1.getCurrentUser());

      // will reuse the session cookies from the file
      Wallhaven wh2 = new Wallhaven(
            getWallhavenUsername(),
            "doesntmatter",
            cookiesFile
      );

      assertNotNull(wh2.getCurrentUser());

      assertEquals(getWallhavenUsername(), wh2.getCurrentUser().getId());
      assertTrue(cookiesFile.delete());
   }
}
