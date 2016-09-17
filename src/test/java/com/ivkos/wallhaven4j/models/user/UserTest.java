package com.ivkos.wallhaven4j.models.user;

import com.ivkos.wallhaven4j.AbstractWallhavenTest;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest extends AbstractWallhavenTest
{
   private static User gandalf;
   private static User ivkos;

   @BeforeClass
   public static void setUpUserTest() throws Exception
   {
      gandalf = getWallhaven().getUser("Gandalf");
      ivkos = getWallhaven().getUser("ivkos");
   }

   @Test
   public void getUsername() throws Exception
   {
      assertEquals("ivkos", ivkos.getId());
      assertEquals("ivkos", ivkos.getUsername());
   }

   @Test
   public void getGroupName() throws Exception
   {
      assertEquals("Developer", gandalf.getGroupName());
      assertEquals("User", ivkos.getGroupName());
   }

   @Test
   public void getDescription() throws Exception
   {
      assertNotNull(gandalf.getDescription());
      assertNull(ivkos.getDescription());
   }

   @Test
   public void getLastActiveTime() throws Exception
   {
      assertNotNull(ivkos.getLastActiveTime());
   }

   @Test
   public void getDateCreated() throws Exception
   {
      assertNotNull(gandalf.getDateCreated());
      assertNotNull(ivkos.getDateCreated());
   }

   @Test
   public void getUploadedWallpapersCount() throws Exception
   {
      assertTrue(gandalf.getUploadedWallpapersCount() > 0);
   }

   @Test
   public void getFavoriteWallpapersCount() throws Exception
   {
      assertTrue(gandalf.getFavoriteWallpapersCount() > 0);
   }

   @Test
   public void getTaggedWallpapersCount() throws Exception
   {
      assertTrue(gandalf.getTaggedWallpapersCount() > 0);
   }

   @Test
   public void getFlaggedWallpapersCount() throws Exception
   {
      assertTrue(gandalf.getFlaggedWallpapersCount() > 0);
   }

   @Test
   public void getSubscribersCount() throws Exception
   {
      assertTrue(gandalf.getSubscribersCount() > 0);
   }

   @Test
   public void getProfileViewsCount() throws Exception
   {
      assertTrue(gandalf.getProfileViewsCount() > 0);
   }

   @Test
   public void getProfileCommentsCount() throws Exception
   {
      assertTrue(gandalf.getProfileCommentsCount() > 0);
   }

   @Test
   public void getForumPostsCount() throws Exception
   {
      assertTrue(gandalf.getForumPostsCount() > 0);
   }

   @Test
   public void getCollections() throws Exception
   {
      assertFalse(gandalf.getCollections().isEmpty());
   }
}
