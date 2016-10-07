package com.ivkos.wallhaven4j.models;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategory;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategoryFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpaper.WallpaperFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;

@Singleton
public class ResourceFactoryFactory
{
   private final TagFactory tagFactory;
   private final TagCategoryFactory tagCategoryFactory;
   private final UserFactory userFactory;
   private final WallpaperFactory wallpaperFactory;
   private final WallpaperCollectionFactory wallpaperCollectionFactory;

   @Inject
   ResourceFactoryFactory(TagFactory tagFactory, TagCategoryFactory tagCategoryFactory, UserFactory userFactory,
                          WallpaperFactory wallpaperFactory, WallpaperCollectionFactory wallpaperCollectionFactory)
   {
      this.tagFactory = tagFactory;
      this.tagCategoryFactory = tagCategoryFactory;
      this.userFactory = userFactory;
      this.wallpaperFactory = wallpaperFactory;
      this.wallpaperCollectionFactory = wallpaperCollectionFactory;
   }

   @SuppressWarnings("unchecked")
   public <I, F extends ResourceFactory<R, I>, R extends AbstractResource<I>> F getFactoryFor(Class<R> resourceClass)
   {
      if (resourceClass == Tag.class) return (F) tagFactory;
      if (resourceClass == TagCategory.class) return (F) tagCategoryFactory;
      if (resourceClass == User.class) return (F) userFactory;
      if (resourceClass == Wallpaper.class) return (F) wallpaperFactory;
      if (resourceClass == WallpaperCollection.class) return (F) wallpaperCollectionFactory;

      throw new IllegalArgumentException("There is no factory for the resource type " +
            resourceClass.getSimpleName());
   }
}
