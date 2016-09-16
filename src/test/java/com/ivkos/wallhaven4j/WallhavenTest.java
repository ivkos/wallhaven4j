package com.ivkos.wallhaven4j;

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
