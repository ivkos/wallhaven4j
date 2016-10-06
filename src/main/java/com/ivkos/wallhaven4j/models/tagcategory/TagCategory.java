package com.ivkos.wallhaven4j.models.tagcategory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;

public class TagCategory extends AbstractResource<Long>
{
   private String name;
   private TagCategory parentCategory;

   @AssistedInject
   TagCategory(WallhavenSession session, @Assisted boolean preloadDom, @Assisted long id)
   {
      super(session, preloadDom, id);
   }

   @AssistedInject
   TagCategory(WallhavenSession session, @Assisted boolean preloadDom, @Assisted long id, @Assisted String name)
   {
      super(session, preloadDom, id);
      this.name = name;
   }

   @AssistedInject
   TagCategory(WallhavenSession session, @Assisted boolean preloadDom, @Assisted long id, @Assisted String name, @Assisted TagCategory parentCategory)
   {
      super(session, preloadDom, id);
      this.name = name;
      this.parentCategory = parentCategory;
   }

   /**
    * Returns the name of the tag category
    *
    * @return the name of the tag category
    */
   public String getName()
   {
      return name;
   }

   /**
    * Returns the parent tag category or <code>null</code> if this is a root tag category (i.e. has no parents)
    *
    * @return the parent tag category or <code>null</code> if this is a root tag category (i.e. has no parents)
    */
   public TagCategory getParentCategory()
   {
      return parentCategory;
   }

   /**
    * Returns the name of the tag category
    *
    * @return the name of the tag category
    */
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
