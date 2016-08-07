package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.models.support.Resource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import org.joda.time.DateTime;

import java.util.Objects;

public class User implements Resource
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
   public String getUrl()
   {
      return UrlPrefixes.URL_USER + "/" + username;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof User)) return false;
      User user = (User) o;
      return Objects.equals(username, user.username);
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(username);
   }
}
