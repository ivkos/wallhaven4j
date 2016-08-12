package com.ivkos.wallhaven4j.models.user;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.support.AbstractResource;
import com.ivkos.wallhaven4j.models.support.UrlPrefixes;
import com.ivkos.wallhaven4j.support.WallhavenSession;
import org.joda.time.DateTime;

public class User extends AbstractResource<String>
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

   @AssistedInject
   User(WallhavenSession session, @Assisted String username)
   {
      super(session);
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
