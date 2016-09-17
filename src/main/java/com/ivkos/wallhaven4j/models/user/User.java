package com.ivkos.wallhaven4j.models.user;

import com.google.common.collect.Collections2;
import com.google.common.net.UrlEscapers;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.htmlparser.TimeElementParser;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Long.parseLong;

public class User extends AbstractResource<String>
{
   private String groupName;
   private String description;

   private DateTime lastActiveTime;
   private DateTime dateCreated;

   private Long uploadedWallpapersCount;
   private Long favoriteWallpapersCount;
   private Long taggedWallpapersCount;
   private Long flaggedWallpapersCount;

   private Long subscribersCount;
   private Long profileViewsCount;
   private Long profileCommentsCount;
   private Long forumPostsCount;

   @AssistedInject
   User(WallhavenSession session, @Assisted boolean preloadDom, @Assisted String id)
   {
      super(session, preloadDom, id);

      if (preloadDom) populateFields();
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_USER + "/" + UrlEscapers.urlPathSegmentEscaper().escape(id);
   }

   public String getUsername()
   {
      return getId();
   }

   @ResourceFieldGetter
   public String getGroupName()
   {
      if (groupName != null) return groupName;

      groupName = OptionalSelector.of(getDom(), "#user > h4.groupname").get().getText();

      return groupName;
   }

   @ResourceFieldGetter
   public String getDescription()
   {
      if (description != null) return description;

      OptionalSelector sel = OptionalSelector.of(getDom(), "#profile-bio");

      // Description is optional
      if (!sel.isPresent()) return null;

      description = sel.get().getText();

      return description;
   }

   @ResourceFieldGetter
   public DateTime getLastActiveTime()
   {
      if (lastActiveTime != null) return lastActiveTime;

      HtmlElement lastActiveLabel = getElementNextToLabel("Last Active");
      if (lastActiveLabel == null) return null;

      HtmlElement timeElement = OptionalSelector.of(lastActiveLabel, "time").get();
      lastActiveTime = TimeElementParser.parse(timeElement);

      return lastActiveTime;
   }

   @ResourceFieldGetter
   public DateTime getDateCreated()
   {
      if (dateCreated != null) return dateCreated;

      HtmlElement timeElement = OptionalSelector.of(getElementNextToLabel("Joined"), "time").get();

      dateCreated = TimeElementParser.parse(timeElement);

      return dateCreated;
   }

   @ResourceFieldGetter
   public long getUploadedWallpapersCount()
   {
      if (uploadedWallpapersCount != null) return uploadedWallpapersCount;

      String uploadsText = OptionalSelector.of(getElementNextToLabel("Uploads"), "dd")
            .get()
            .getText()
            .replace(",", "");

      uploadedWallpapersCount = parseLong(uploadsText);

      return uploadedWallpapersCount;
   }

   @ResourceFieldGetter
   public Long getFavoriteWallpapersCount()
   {
      if (favoriteWallpapersCount != null) return favoriteWallpapersCount;

      String favsText = OptionalSelector.of(getElementNextToLabel("Favorites"), "dd")
            .get()
            .getText()
            .replace(",", "");

      favoriteWallpapersCount = parseLong(favsText);

      return favoriteWallpapersCount;
   }

   @ResourceFieldGetter
   public Long getTaggedWallpapersCount()
   {
      if (taggedWallpapersCount != null) return taggedWallpapersCount;

      String taggedText = OptionalSelector.of(getElementNextToLabel("Wallpapers Tagged"), "dd")
            .get()
            .getText()
            .replace(",", "");

      taggedWallpapersCount = parseLong(taggedText);

      return taggedWallpapersCount;
   }

   @ResourceFieldGetter
   public Long getFlaggedWallpapersCount()
   {
      if (flaggedWallpapersCount != null) return flaggedWallpapersCount;

      String flaggedText = OptionalSelector.of(getElementNextToLabel("Wallpapers Flagged"), "dd")
            .get()
            .getText()
            .replace(",", "");

      flaggedWallpapersCount = parseLong(flaggedText);

      return flaggedWallpapersCount;
   }

   @ResourceFieldGetter
   public Long getSubscribersCount()
   {
      if (subscribersCount != null) return subscribersCount;

      String flaggedText = OptionalSelector.of(getElementNextToLabel("Subscribers"), "dd")
            .get()
            .getText()
            .replace(",", "");

      subscribersCount = parseLong(flaggedText);

      return subscribersCount;
   }

   @ResourceFieldGetter
   public Long getProfileViewsCount()
   {
      if (profileViewsCount != null) return profileViewsCount;

      String flaggedText = OptionalSelector.of(getElementNextToLabel("Profile Views"), "dd")
            .get()
            .getText()
            .replace(",", "");

      profileViewsCount = parseLong(flaggedText);

      return profileViewsCount;
   }

   @ResourceFieldGetter
   public Long getProfileCommentsCount()
   {
      if (profileCommentsCount != null) return profileCommentsCount;

      String flaggedText = OptionalSelector.of(getElementNextToLabel("Profile Comments"), "dd")
            .get()
            .getText()
            .replace(",", "");

      profileCommentsCount = parseLong(flaggedText);

      return profileCommentsCount;
   }

   @ResourceFieldGetter
   public Long getForumPostsCount()
   {
      if (forumPostsCount != null) return forumPostsCount;

      String flaggedText = OptionalSelector.of(getElementNextToLabel("Forum Posts"), "dd")
            .get()
            .getText()
            .replace(",", "");

      forumPostsCount = parseLong(flaggedText);

      return forumPostsCount;
   }

   private HtmlElement getElementNextToLabel(String labelContent)
   {
      List<HtmlElement> dts = getDom().find("dl.datalist > dt");

      ArrayList<HtmlElement> list = newArrayList(Collections2.filter(dts, e -> labelContent.equals(e.getText())));

      if (list.isEmpty()) return null;

      return list.get(0).getNextElementSibling();
   }
}
