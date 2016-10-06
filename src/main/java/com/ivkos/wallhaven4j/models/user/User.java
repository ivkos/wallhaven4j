package com.ivkos.wallhaven4j.models.user;

import com.google.common.collect.Collections2;
import com.google.common.net.UrlEscapers;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionIdentifier;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.htmlparser.TimeElementParser;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.forResource;
import static java.lang.Long.parseLong;
import static java.util.Collections.unmodifiableList;

public class User extends AbstractResource<String>
{
   private final ResourceFactoryFactory rff;

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
   User(WallhavenSession session, ResourceFactoryFactory rff, @Assisted boolean preloadDom, @Assisted String id)
   {
      super(session, preloadDom, id);
      this.rff = rff;

      if (preloadDom) populateFields();
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_USER + "/" + UrlEscapers.urlPathSegmentEscaper().escape(id);
   }

   /**
    * Returns the username
    *
    * @return the username
    */
   public String getUsername()
   {
      return getId();
   }

   /**
    * Returns the user group's name this user belongs to (e.g. Developer, User, Administrator, ...)
    *
    * @return the user group's name this user belongs to
    */
   @ResourceFieldGetter
   public String getGroupName()
   {
      if (groupName != null) return groupName;

      groupName = OptionalSelector.of(getDom(), "#user > h4.groupname").get().getText();

      return groupName;
   }

   /**
    * Returns the user's description text or <code>null</code> if none is set
    *
    * @return the user's description text or <code>null</code> if none is set
    */
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

   /**
    * Returns the last time the user has been active online
    *
    * @return the last time the user has been active online
    */
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

   /**
    * Returns the date and time this user account has been created
    *
    * @return the date and time this user account has been created
    */
   @ResourceFieldGetter
   public DateTime getDateCreated()
   {
      if (dateCreated != null) return dateCreated;

      HtmlElement timeElement = OptionalSelector.of(getElementNextToLabel("Joined"), "time").get();

      dateCreated = TimeElementParser.parse(timeElement);

      return dateCreated;
   }

   /**
    * Returns the number of wallpaper this user has uploaded
    *
    * @return the number of wallpaper this user has uploaded
    */
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

   /**
    * Returns the number of favorite wallpapers the user has
    *
    * @return the number of favorite wallpapers the user has
    */
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

   /**
    * Returns the number of wallpapers this user has tagged
    *
    * @return the number of wallpapers this user has tagged
    */
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

   /**
    * Returns the number of wallpapers this user has flagged
    *
    * @return the number of wallpapers this user has flagged
    */
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

   /**
    * Returns the number of users subscribed to this user
    *
    * @return the number of users subscribed to this user
    */
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

   /**
    * Returns the number of views the user's profile has received
    *
    * @return the number of views the user's profile has received
    */
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

   /**
    * Returns the number of comments on the user's profile page
    *
    * @return the number of comments on the user's profile page
    */
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

   /**
    * Returns the number of posts the user has posted on the forum
    *
    * @return the number of posts the user has posted on the forum
    */
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

   /**
    * Returns a list of the user's wallpaper collections
    *
    * @return a list of the user's wallpaper collections
    */
   public List<WallpaperCollection> getCollections()
   {
      String url = getUrl() + "/favorites";

      String html = getSession().getHttpClient().get(url).getBody();
      HtmlElement dom = getSession().getHtmlParser().parse(html, url);

      List<HtmlElement> lis = dom.find("ul#collections > li.collection");

      return unmodifiableList(newArrayList(transform(lis, input -> {
         DescriptiveParseExceptionSupplier supplier = forResource(WallpaperCollection.class, "collection link in user's page");

         HtmlElement a = OptionalSelector.of(input, "a")
               .orElseThrow(supplier);

         String href = a.getAttribute("href").trim();
         Pattern pattern = Pattern.compile("^" + Pattern.quote(getUrl() + "/favorites/") + "(\\d+)$");
         Matcher matcher = pattern.matcher(href);

         if (!matcher.matches()) {
            throw supplier.get();
         }

         long id = 0;
         try {
            id = parseLong(matcher.group(1));
         } catch (NumberFormatException e) {
            throw supplier.get(e);
         }

         String name = OptionalSelector.of(a, "span.collection-label")
               .orElseThrow(forResource(WallpaperCollection.class, "collection name")).getText();

         return ((WallpaperCollectionFactory) rff.getFactoryFor(WallpaperCollection.class))
               .create(false, new WallpaperCollectionIdentifier(id, this), name);
      })));
   }

   private HtmlElement getElementNextToLabel(String labelContent)
   {
      List<HtmlElement> dts = getDom().find("dl.datalist > dt");

      ArrayList<HtmlElement> list = newArrayList(Collections2.filter(dts, e -> labelContent.equals(e.getText())));

      if (list.isEmpty()) return null;

      return list.get(0).getNextElementSibling();
   }
}
