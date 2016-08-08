package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import org.joda.time.DateTime;

public class User extends Resource<String>
{
   private final String username;

   private DateTime lastActiveTime;
   private DateTime dateCreated;

   private long uploadedWallpapersCount;
   private long favoriteWallpapersCount;
   private long taggedWallpapersCount;
   private long flaggedWallpapersCount;

   private long subscibersCount;
   private long profileViewsCount;
   private long profileCommentsCounts;
   private long forumPostsCount;

   public User(String username)
   {
      this.username = username;
   }

   @Override
   public String getId()
   {
      return username;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_USER + "/" + username;
   }
}
