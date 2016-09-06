package com.ivkos.wallhaven4j.models.user;

import com.google.common.net.UrlEscapers;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import org.joda.time.DateTime;

public class User extends AbstractResource<String>
{
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
   User(WallhavenSession session, @Assisted boolean preloadDom, @Assisted String id)
   {
      super(session, preloadDom, id);
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_USER + "/" + UrlEscapers.urlPathSegmentEscaper().escape(id);
   }
}
