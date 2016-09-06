package com.ivkos.wallhaven4j.models.tag;


import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.support.enums.Purity;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import org.joda.time.DateTime;

public class Tag extends AbstractResource<Long>
{
   private String name;
   private Purity purity;
   private TagCategory category;
   private String[] aliases;

   private long taggedWallpapersCount;
   private long taggedWallpapersViewCount;
   private long subscribersCount;

   private User user;
   private DateTime dateCreated;

   @AssistedInject
   Tag(WallhavenSession session, @Assisted boolean preloadDom, @Assisted long id)
   {
      super(session, preloadDom, id);
   }

   @AssistedInject
   Tag(WallhavenSession session, @Assisted boolean preloadDom, @Assisted long id, @Assisted String name, @Assisted Purity purity)
   {
      super(session, preloadDom, id);
      this.name = name;
      this.purity = purity;
   }

   @Override
   public String toString()
   {
      return name;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_TAG + "/" + id;
   }
}
