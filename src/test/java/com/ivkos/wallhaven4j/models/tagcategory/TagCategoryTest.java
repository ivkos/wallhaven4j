package com.ivkos.wallhaven4j.models.tagcategory;

import com.ivkos.wallhaven4j.AbstractWallhavenTest;
import com.ivkos.wallhaven4j.models.tag.Tag;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagCategoryTest extends AbstractWallhavenTest
{
   private static Tag nature;
   private static Tag cat;

   private static TagCategory natureCategory;
   private static TagCategory animalsCategory;

   @BeforeClass
   public static void setUp() throws Exception
   {
      nature = getWallhaven().getTag(37);
      cat = getWallhaven().getTag(43);

      assertNotNull(nature);
      assertNotNull(cat);

      natureCategory = nature.getCategory();
      animalsCategory = cat.getCategory();

      assertNotNull(natureCategory);
      assertNotNull(animalsCategory);
   }

   @Test
   public void getName() throws Exception
   {
      assertEquals("Nature", natureCategory.getName());
      assertEquals("Animals", animalsCategory.getName());
   }

   @Test
   public void getParentCategory() throws Exception
   {
      assertNull(natureCategory.getParentCategory());

      assertEquals(natureCategory, animalsCategory.getParentCategory());

      assertNull(animalsCategory.getParentCategory().getParentCategory());
   }

   @Test
   public void toAString() throws Exception
   {
      assertEquals("Nature", natureCategory.toString());
      assertEquals("Animals", animalsCategory.toString());
   }
}
