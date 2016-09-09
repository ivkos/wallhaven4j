package com.ivkos.wallhaven4j.models.tag;

import com.ivkos.wallhaven4j.WallhavenTestBase;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagTest extends WallhavenTestBase
{
   private Tag nature = getWallhaven().getTag(37);
   private Tag trees = getWallhaven().getTag(115);

   @Test
   public void getName() throws Exception
   {
      assertEquals("nature", nature.getName());
   }

   @Test
   public void getPurity() throws Exception
   {
      assertEquals(Purity.SFW, nature.getPurity());
   }

   @Test
   public void getCategory() throws Exception
   {
      assertNotNull(nature.getCategory());
      assertEquals("Nature", nature.getCategory().getName());
      assertNull(nature.getCategory().getParentCategory());

      assertNotNull(trees.getCategory());
      assertNotNull(trees.getCategory().getParentCategory());
   }

   @Test
   public void getAliases() throws Exception
   {
      assertFalse(nature.getAliases().isEmpty());
   }

   @Test
   public void getTaggedWallpapersCount() throws Exception
   {
      assertTrue(nature.getTaggedWallpapersCount() > 0);
   }

   @Test
   public void getTaggedWallpapersViewCount() throws Exception
   {
      assertTrue(nature.getTaggedWallpapersViewCount() > 0);
   }

   @Test
   public void getSubscribersCount() throws Exception
   {
      assertTrue(nature.getSubscribersCount() > 0);
   }

   @Test
   public void getUser() throws Exception
   {
      assertEquals("SuperBastard", nature.getUser().getId());
   }

   @Test
   public void getDateCreated() throws Exception
   {
      assertNotNull(nature.getDateCreated());
   }
}
