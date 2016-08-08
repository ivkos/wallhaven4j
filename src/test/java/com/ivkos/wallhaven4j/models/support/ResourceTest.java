package com.ivkos.wallhaven4j.models.support;

import com.ivkos.wallhaven4j.models.Tag;
import com.ivkos.wallhaven4j.models.WallpaperCollection;
import org.junit.Assert;
import org.junit.Test;

public class ResourceTest
{
   @Test
   public void equalObjectsAreEqual()
   {
      Tag tag1 = new Tag(1, "somename");
      Tag tag2 = new Tag(1, "somename");

      Assert.assertTrue(tag1.equals(tag2));
      Assert.assertTrue(tag1.hashCode() == tag2.hashCode());
   }

   @Test
   public void inequalObjectsAreInequal()
   {
      Tag tag1 = new Tag(1, "somename");
      WallpaperCollection wallpaperCollection = new WallpaperCollection(1, null, "somename");

      Assert.assertFalse(tag1.equals(wallpaperCollection));
      Assert.assertFalse(tag1.hashCode() == wallpaperCollection.hashCode());
   }
}
