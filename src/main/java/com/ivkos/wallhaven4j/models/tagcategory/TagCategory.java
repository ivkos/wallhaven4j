package com.ivkos.wallhaven4j.models.tagcategory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.support.AbstractResource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import com.ivkos.wallhaven4j.support.WallhavenSession;

public class TagCategory extends AbstractResource<Long>
{
   private final long id;
   private String name;

   private TagCategory parentCategory;

   @AssistedInject
   TagCategory(WallhavenSession session, @Assisted long id)
   {
      super(session);
      this.id = id;
   }

   @AssistedInject
   TagCategory(WallhavenSession session, @Assisted long id, @Assisted String name)
   {
      super(session);
      this.id = id;
      this.name = name;
   }

   @AssistedInject
   TagCategory(WallhavenSession session, @Assisted long id, @Assisted String name, @Assisted TagCategory parentCategory)
   {
      super(session);
      this.id = id;
      this.name = name;
      this.parentCategory = parentCategory;
   }

   @Override
   public Long getId()
   {
      return id;
   }

   public String getName()
   {
      return name;
   }

   public TagCategory getParentCategory()
   {
      return parentCategory;
   }

   @Override
   public String toString()
   {
      return name;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_TAGS + "/" + id;
   }
}
