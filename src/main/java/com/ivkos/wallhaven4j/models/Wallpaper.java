package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.enums.Category;
import com.ivkos.wallhaven4j.models.enums.Purity;
import com.ivkos.wallhaven4j.models.support.Color;
import com.ivkos.wallhaven4j.models.support.Resolution;
import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import org.joda.time.DateTime;

import java.util.List;

public class Wallpaper implements Resource
{
   private long id;
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

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_WALLPAPER + "/" + id;
   }
}
