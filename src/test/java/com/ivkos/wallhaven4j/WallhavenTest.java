package com.ivkos.wallhaven4j;

import com.ivkos.wallhaven4j.models.misc.Ratio;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.exceptions.LoginException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;
import com.ivkos.wallhaven4j.util.searchquery.SearchQuery;
import com.ivkos.wallhaven4j.util.searchquery.SearchQueryBuilder;
import org.junit.Test;

import java.io.File;
import java.util.List;

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
   public void searchWithQuery() throws Exception
   {
      List<Wallpaper> result1 = getWallhaven().search(new SearchQueryBuilder()
            .keywords("cars")
            .resolutions(new Resolution(1920, 1080))
            .sorting(Sorting.VIEWS)
            .order(Order.DESC)
            .build()
      );
      assertFalse(result1.isEmpty());

      List<Wallpaper> result2 = getWallhaven(true).search(new SearchQueryBuilder()
            .sorting(Sorting.FAVORITES)
            .order(Order.DESC)
            .build()
      );
      assertFalse(result2.isEmpty());
   }

   @Test
   public void searchLatest() throws Exception
   {
      List<Wallpaper> result = getWallhaven().search(new SearchQueryBuilder()
            .sorting(Sorting.DATE_ADDED)
            .order(Order.DESC)
            .build());

      assertFalse(result.isEmpty());
   }

   @Test(expected = NullPointerException.class)
   public void searchWithNullQuery() throws Exception
   {
      getWallhaven().search(null);
   }

   @Test
   public void searchPageWithQuery() throws Exception
   {
      SearchQuery query = new SearchQueryBuilder()
            .keywords("minimal")
            .categories(Category.GENERAL)
            .ratios(new Ratio(9, 16))
            .build();

      List<Wallpaper> page2 = getWallhaven().search(query, 2);

      assertFalse(page2.isEmpty());
   }

   @Test(expected = NullPointerException.class)
   public void searchPageWithNullQuery() throws Exception
   {
      getWallhaven().search(null, 1);
   }

   @Test(expected = IllegalArgumentException.class)
   public void searchPageWithZeroPage() throws Exception
   {
      getWallhaven().search(new SearchQueryBuilder().build(), 0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void searchPageWithNegativePage() throws Exception
   {
      getWallhaven().search(new SearchQueryBuilder().build(), -1);
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
