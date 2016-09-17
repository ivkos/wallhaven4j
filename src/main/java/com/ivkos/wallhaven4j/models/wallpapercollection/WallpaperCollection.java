package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.jsonserializer.JsonSerializer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.X_REQUESTED_WITH;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.of;
import static java.lang.Long.parseLong;
import static java.util.Collections.unmodifiableList;

public class WallpaperCollection extends AbstractResource<WallpaperCollectionIdentifier>
{
   private final JsonSerializer jsonSerializer;
   private final WallpaperCollectionTransformers transformers;

   private String name;

   private Long wallpapersCount;
   private Long viewsCount;
   private Long subscribersCount;
   private List<User> subscribers;

   @AssistedInject
   WallpaperCollection(WallhavenSession session,
                       JsonSerializer jsonSerializer,
                       WallpaperCollectionTransformers transformers,
                       @Assisted boolean preloadDom,
                       @Assisted WallpaperCollectionIdentifier id)
   {
      super(session, preloadDom, id);
      this.jsonSerializer = jsonSerializer;
      this.transformers = transformers;

      if (preloadDom) populateFields();
   }

   @AssistedInject
   WallpaperCollection(WallhavenSession session,
                       JsonSerializer jsonSerializer,
                       WallpaperCollectionTransformers transformers,
                       @Assisted boolean preloadDom,
                       @Assisted WallpaperCollectionIdentifier id,
                       @Assisted String name)
   {
      super(session, preloadDom, id);
      this.jsonSerializer = jsonSerializer;
      this.transformers = transformers;
      this.name = name;

      if (preloadDom) populateFields();
   }

   public User getUser()
   {
      return getId().getUser();
   }

   @Override
   public String getUrl()
   {
      return getUser().getUrl() + "/favorites/" + id.getLongId();
   }

   @Override
   public String toString()
   {
      return name;
   }

   @ResourceFieldGetter
   public String getName()
   {
      if (name != null) return name;

      HtmlElement nameElement = OptionalSelector.of(getDom(), "#profile-content > header > h1").orElseThrowSupplied(of(this, "name"));

      this.name = nameElement.getText();

      return this.name;
   }

   @ResourceFieldGetter
   public Long getWallpapersCount()
   {
      if (wallpapersCount != null) return wallpapersCount;

      HtmlElement countElement = OptionalSelector.of(getDom(), "#profile-content > header > div > span:nth-child(1)")
            .orElseThrowSupplied(of(this, "wallpapers count"));

      String countText = countElement.getText().trim().replace(",", "");

      try {
         wallpapersCount = parseLong(countText);
      } catch (NumberFormatException e) {
         throw of(this, "wallpapers count").get(e);
      }

      return wallpapersCount;
   }

   @ResourceFieldGetter
   public Long getViewsCount()
   {
      if (viewsCount != null) return viewsCount;

      HtmlElement countElement = OptionalSelector.of(getDom(), "#profile-content > header > div > span:nth-child(2)")
            .orElseThrowSupplied(of(this, "views count"));

      String countText = countElement.getText().trim().replace(",", "");

      try {
         viewsCount = parseLong(countText);
      } catch (NumberFormatException e) {
         throw of(this, "views count").get(e);
      }

      return viewsCount;
   }

   @ResourceFieldGetter
   public Long getSubscribersCount()
   {
      if (subscribersCount != null) return subscribersCount;

      HtmlElement countElement = OptionalSelector.of(getDom(), "#profile-content > header > div > span:nth-child(3) > a")
            .orElseThrowSupplied(of(this, "subscribers count"));

      String countText = countElement.getText().trim().replace(",", "");

      try {
         subscribersCount = parseLong(countText);
      } catch (NumberFormatException e) {
         throw of(this, "subscribers count").get(e);
      }

      return subscribersCount;
   }

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

      subscribers = unmodifiableList(newArrayList(transform(userlist, transformers::transformSubscribers)));

      return subscribers;
   }

   // TODO: Implement
   public List<Wallpaper> getWallpapers()
   {
      return null;
   }

   private static class XhrViewResponse
   {
      public String view;
   }
}
