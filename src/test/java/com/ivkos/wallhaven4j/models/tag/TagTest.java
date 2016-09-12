package com.ivkos.wallhaven4j.models.tag;

import com.ivkos.wallhaven4j.AbstractWallhavenTest;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagTest extends AbstractWallhavenTest
{
   private static Tag nature;
   private static Tag trees;

   @BeforeClass
   public static void setUp() throws Exception
   {
      nature = getWallhaven().getTag(37);
      trees = getWallhaven().getTag(115);

      assertNotNull(nature);
      assertNotNull(trees);
   }

   @Test
   public void toAString() throws Exception
   {
      assertEquals("#nature", nature.toString());
      assertEquals("#trees", trees.toString());
   }

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
