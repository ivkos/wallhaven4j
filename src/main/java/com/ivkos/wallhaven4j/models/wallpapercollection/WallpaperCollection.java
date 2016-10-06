package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.util.BitfieldSum;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.jsonserializer.JsonSerializer;
import com.ivkos.wallhaven4j.util.pagecrawler.thumbnailpage.ThumbnailPageCrawlerFactory;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.X_REQUESTED_WITH;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.forResource;
import static java.lang.Long.parseLong;
import static java.util.Collections.unmodifiableList;

public class WallpaperCollection extends AbstractResource<WallpaperCollectionIdentifier>
{
   private final JsonSerializer jsonSerializer;
   private final WallpaperCollectionTransformers collectionTransformers;
   private final ThumbnailPageCrawlerFactory thumbnailPageCrawlerFactory;

   private String name;

   private Long wallpapersCount;
   private Long viewsCount;
   private Long subscribersCount;
   private List<User> subscribers;

   @AssistedInject
   WallpaperCollection(WallhavenSession session,
                       JsonSerializer jsonSerializer,
                       WallpaperCollectionTransformers collectionTransformers,
                       ThumbnailPageCrawlerFactory thumbnailPageCrawlerFactory,
                       @Assisted boolean preloadDom,
                       @Assisted WallpaperCollectionIdentifier id)
   {
      super(session, preloadDom, id);
      this.jsonSerializer = jsonSerializer;
      this.collectionTransformers = collectionTransformers;
      this.thumbnailPageCrawlerFactory = thumbnailPageCrawlerFactory;

      if (preloadDom) populateFields();
   }

   @AssistedInject
   WallpaperCollection(WallhavenSession session,
                       JsonSerializer jsonSerializer,
                       WallpaperCollectionTransformers collectionTransformers,
                       ThumbnailPageCrawlerFactory thumbnailPageCrawlerFactory,
                       @Assisted boolean preloadDom,
                       @Assisted WallpaperCollectionIdentifier id,
                       @Assisted String name)
   {
      super(session, preloadDom, id);
      this.jsonSerializer = jsonSerializer;
      this.collectionTransformers = collectionTransformers;
      this.thumbnailPageCrawlerFactory = thumbnailPageCrawlerFactory;

      this.name = name;

      if (preloadDom) populateFields();
   }

   /**
    * Returns the user who has created this collection
    *
    * @return the user who has created this collection
    */
   public User getUser()
   {
      return getId().getUser();
   }

   @Override
   public String getUrl()
   {
      return getUser().getUrl() + "/favorites/" + id.getLongId();
   }

   /**
    * Returns the name of the collection
    *
    * @return the name of the collection
    */
   @Override
   public String toString()
   {
      return name;
   }

   /**
    * Returns the name of the collection
    *
    * @return the name of the collection
    */
   @ResourceFieldGetter
   public String getName()
   {
      if (name != null) return name;

      HtmlElement nameElement = OptionalSelector.of(getDom(), "#profile-content > header > h1")
            .orElseThrow(forResource(this, "name"));

      this.name = nameElement.getText();

      return this.name;
   }

   /**
    * Returns the number of wallpapers in the collection
    *
    * @return the number of wallpapers in the collection
    */
   @ResourceFieldGetter
   public Long getWallpapersCount()
   {
      if (wallpapersCount != null) return wallpapersCount;

      HtmlElement countElement = OptionalSelector.of(getDom(), "#profile-content > header > div > span:nth-child(1)")
            .orElseThrow(forResource(this, "wallpapers count"));

      String countText = countElement.getText().trim().replace(",", "");

      try {
         wallpapersCount = parseLong(countText);
      } catch (NumberFormatException e) {
         throw forResource(this, "wallpapers count").get(e);
      }

      return wallpapersCount;
   }

   /**
    * Returns the number of times the collection has been viewed
    *
    * @return the number of times the collection has been viewed
    */
   @ResourceFieldGetter
   public Long getViewsCount()
   {
      if (viewsCount != null) return viewsCount;

      HtmlElement countElement = OptionalSelector.of(getDom(), "#profile-content > header > div > span:nth-child(2)")
            .orElseThrow(forResource(this, "views count"));

      String countText = countElement.getText().trim().replace(",", "");

      try {
         viewsCount = parseLong(countText);
      } catch (NumberFormatException e) {
         throw forResource(this, "views count").get(e);
      }

      return viewsCount;
   }

   /**
    * Returns the number of users subscribed to this collection
    *
    * @return the number of users subscribed to this collection
    */
   @ResourceFieldGetter
   public Long getSubscribersCount()
   {
      if (subscribersCount != null) return subscribersCount;

      HtmlElement countElement = OptionalSelector.of(getDom(), "#profile-content > header > div > span:nth-child(3) > a")
            .orElseThrow(forResource(this, "subscribers count"));

      String countText = countElement.getText().trim().replace(",", "");

      try {
         subscribersCount = parseLong(countText);
      } catch (NumberFormatException e) {
         throw forResource(this, "subscribers count").get(e);
      }

      return subscribersCount;
   }

   /**
    * Returns a list of the users subscribed to this collection. Note that the list can be obtained only for collections
    * owned by the currently logged in user. This method will always throw {@link ResourceNotAccessibleException} if
    * this requirement is not met.
    *
    * @return a list of the users subscribed to this collection
    * @throws ResourceNotAccessibleException if the collection is not owned by the currently logged in user,
    *                                        or the current wallhaven session is anonymous
    */
   public List<User> getSubscribers()
   {
      if (subscribers != null) return subscribers;

      if (getSession().getCurrentUser() == null) {
         throw new ResourceNotAccessibleException("Subscribers list of collection is not accessible when not logged in");
      }

      if (!getId().getUser().equals(getSession().getCurrentUser())) {
         throw new ResourceNotAccessibleException(
               "Subscribers list of collection is not accessible because the current user does not own the collection");
      }

      String url = UrlPrefixes.URL_USER + "/favorites/" + this.getId().getLongId() + "/subscribers";

      String response = getSession().getHttpClient().get(url, ImmutableMap.of(
            X_REQUESTED_WITH, "XMLHttpRequest",
            CONTENT_TYPE, JSON_UTF_8.toString()
      )).getBody();

      XhrViewResponse xhrViewResponse = jsonSerializer.fromJson(response, XhrViewResponse.class);
      HtmlElement document = getSession().getHtmlParser().parse(xhrViewResponse.view, getUrl());
      List<HtmlElement> userlist = document.find("ul.userlist > li");

      subscribers = unmodifiableList(newArrayList(transform(userlist, collectionTransformers::transformSubscribers)));

      return subscribers;
   }

   /**
    * Fetches a list of the wallpapers that are in the collection
    *
    * @param pageCount Number of pages to fetch. One page could contain 24 (default), 32 or 64 wallpapers,
    *                  depending on the preference of the logged in user, set in Wallhaven's settings page.
    * @param purities  The purity of wallpapers to include in result. If no purity is specified,
    *                  the account defaults will be used when logged in.
    * @return a list of wallpapers in the collection, newly added first
    */
   public List<Wallpaper> getWallpapers(long pageCount, Purity... purities)
   {
      return thumbnailPageCrawlerFactory
            .create(getUrl())
            .getPageSequence(pageCount, ImmutableMap.of(
                  "purity", BitfieldSum.asThreeBitBinaryString(purities)
            ));
   }

   private static class XhrViewResponse
   {
      public String view;
   }
}
