package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.support.AbstractResource;
import com.ivkos.wallhaven4j.models.support.Color;
import com.ivkos.wallhaven4j.models.support.Resolution;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import com.ivkos.wallhaven4j.models.support.enums.Category;
import com.ivkos.wallhaven4j.models.support.enums.Purity;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.support.WallhavenSession;
import org.joda.time.DateTime;

import java.util.List;

public class Wallpaper extends AbstractResource<Long>
{
   private Resolution resolution;
   private List<Color> colors;
   private List<Tag> tags;
   private Purity purity;
   private User user;
   private DateTime dateCreated;
   private Category category;
   private long size;
   private long viewsCount;
   private List<WallpaperCollection> collections;

   @AssistedInject
   Wallpaper(WallhavenSession session, @Assisted boolean preloadDom, @Assisted long id)
   {
      super(session, preloadDom, id);
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_WALLPAPER + "/" + id;
   }
}
