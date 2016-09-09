package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.misc.Color;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.TimeElementParser;
import com.ivkos.wallhaven4j.util.jsonserializer.JsonSerializer;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.X_REQUESTED_WITH;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static com.ivkos.wallhaven4j.models.misc.enums.Category.*;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;
import static com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector.of;
import static java.lang.Long.parseLong;
import static java.util.Collections.unmodifiableList;

public class Wallpaper extends AbstractResource<Long>
{
   private final JsonSerializer jsonSerializer;
   private final TagFactory tagFactory;
   private final UserFactory userFactory;
   private final WallpaperCollectionFactory wallpaperCollectionFactory;
   private final FavoritesToWallpaperCollectionTransformer favoritesToWallpaperCollectionTransformer;

   private Resolution resolution;
   private List<Color> colors;
   private List<Tag> tags;
   private Purity purity;
   private User user;
   private DateTime dateCreated;
   private Category category;
   private Double size;
   private Long viewsCount;

   private List<WallpaperCollection> collections;

   @AssistedInject
   Wallpaper(WallhavenSession session,
             JsonSerializer jsonSerializer,
             TagFactory tagFactory,
             UserFactory userFactory,
             WallpaperCollectionFactory wallpaperCollectionFactory,
             FavoritesToWallpaperCollectionTransformer favoritesToWallpaperCollectionTransformer,
             @Assisted boolean preloadDom,
             @Assisted long id)
   {
      super(session, preloadDom, id);
      this.jsonSerializer = jsonSerializer;
      this.tagFactory = tagFactory;
      this.userFactory = userFactory;
      this.wallpaperCollectionFactory = wallpaperCollectionFactory;
      this.favoritesToWallpaperCollectionTransformer = favoritesToWallpaperCollectionTransformer;

      if (preloadDom) populateFields();
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_WALLPAPER + "/" + id;
   }

   @ResourceFieldGetter
   public Resolution getResolution()
   {
      if (resolution != null) {
         return resolution;
      }

      HtmlElement wallpaperElement = of(getDom(), "#wallpaper").get();
      Map<String, String> dataset = wallpaperElement.getDataAttributes();

      String width = checkNotNull(dataset.get("wallpaper-width"), "could not parse resolution");
      String height = checkNotNull(dataset.get("wallpaper-height"), "could not parse resolution");

      resolution = new Resolution(parseLong(width), parseLong(height));

      return resolution;
   }

   @ResourceFieldGetter
   public List<Color> getColors()
   {
      if (colors != null) {
         return colors;
      }

      List<HtmlElement> colorElements = getDom().find("ul.color-palette > li.color");

      colors = unmodifiableList(newArrayList(transform(colorElements, new Function<HtmlElement, Color>()
      {
         private final Pattern PATTERN_COLOR = Pattern.compile("background-color:(#[0-9A-Fa-f]{6})");

         @Override
         public Color apply(HtmlElement input)
         {
            Matcher matcher = PATTERN_COLOR.matcher(input.getAttribute("style"));

            if (!matcher.matches()) throw new ParseException("Could not parse color");

            String hex = matcher.group(1);

            return new Color(hex);
         }
      })));

      return colors;
   }

   @ResourceFieldGetter
   public List<Tag> getTags()
   {
      if (tags != null) return tags;

      List<HtmlElement> tagElements = getDom().find("ul#tags > li.tag");

      tags = unmodifiableList(newArrayList(transform(tagElements, new Function<HtmlElement, Tag>()
      {
         @Override
         public Tag apply(HtmlElement input)
         {
            long tagId = parseLong(input.getDataAttributes().get("tag-id"));
            String tagName = input.findFirst("a.tagname").getText();

            Purity tagPurity = input.hasClass("tag-sfw") ? SFW
                  : input.hasClass("tag-sketchy") ? SKETCHY
                  : input.hasClass("tag-nsfw") ? NSFW
                  : null;

            if (tagPurity == null) throw new ParseException("Could not parse purity of tag");

            return tagFactory.create(false, tagId, tagName, tagPurity);
         }
      })));

      return tags;
   }

   @ResourceFieldGetter
   public Purity getPurity()
   {
      if (purity != null) return purity;

      HtmlElement purityLabel = of(getDom(), "#wallpaper-purity-form > fieldset > label.purity").get();

      purity = purityLabel.hasClass("sfw") ? SFW
            : purityLabel.hasClass("sketchy") ? SKETCHY
            : purityLabel.hasClass("nsfw") ? NSFW
            : null;

      if (purity == null) throw new ParseException("Could not parse purity of wallpaper");

      return purity;
   }

   @ResourceFieldGetter
   public User getUser()
   {
      if (user != null) return user;

      HtmlElement uploaderElement = of(getDom().findElementById("showcase-sidebar"), "dd.showcase-uploader > a.username").get();

      String username = uploaderElement.getText();

      user = userFactory.create(false, username);

      return user;
   }

   @ResourceFieldGetter
   public DateTime getDateCreated()
   {
      if (dateCreated != null) return dateCreated;

      HtmlElement timeElement = of(getDom().findElementById("showcase-sidebar"), "dd.showcase-uploader > time").get();

      dateCreated = TimeElementParser.parse(timeElement);

      return dateCreated;
   }

   @ResourceFieldGetter
   public Category getCategory()
   {
      if (category != null) return category;

      String categoryText = of(getDom(),
            "div.sidebar-content > div.sidebar-section[data-storage-id=showcase-info] > dl > dd:nth-child(4)"
      ).get().getText();

      switch (categoryText) {
         case "General":
            category = GENERAL;
            break;
         case "Anime":
            category = ANIME;
            break;
         case "People":
            category = PEOPLE;
            break;
         default:
            throw new ParseException("Could not parse wallpaper category");
      }

      return category;
   }

   /**
    * @return the size of the wallpaper in KiB
    */
   @ResourceFieldGetter
   public double getSize()
   {
      if (size != null) return size;

      String sizeText = of(getDom(),
            "div.sidebar-content > div.sidebar-section[data-storage-id=showcase-info] > dl > dd:nth-child(6)"
      ).get().getText();

      Pattern pattern = Pattern.compile("([\\d.,]+)\\s(KiB|MiB)");
      Matcher matcher = pattern.matcher(sizeText);

      if (!matcher.find()) {
         throw new ParseException("Could not parse size: " + sizeText);
      }

      String sizeString = matcher.group(1).trim().replace(",", "");
      String unit = matcher.group(2);

      long multiplier = unit.equals("KiB") ? 1
            : unit.equals("MiB") ? 1024
            : 0;

      size = Double.parseDouble(sizeString) * multiplier;

      return size;
   }

   @ResourceFieldGetter
   public long getViewsCount()
   {
      if (viewsCount != null) return viewsCount;

      String viewsString = of(getDom(),
            "div.sidebar-content > div.sidebar-section[data-storage-id=showcase-info] > dl > dd:nth-child(8)"
      ).get().getText().trim().replace(",", "");


      try {
         viewsCount = parseLong(viewsString);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse views count", e);
      }

      return viewsCount;
   }

   public List<WallpaperCollection> getCollections()
   {
      if (collections != null) return collections;

      String response = getSession().getHttpClient().get(getUrl() + "/favorites", ImmutableMap.of(
            X_REQUESTED_WITH, "XMLHttpRequest",
            CONTENT_TYPE, JSON_UTF_8.toString()
      )).getBody();

      XhrViewResponse xhrViewResponse = jsonSerializer.fromJson(response, XhrViewResponse.class);
      HtmlElement document = getSession().getHtmlParser().parse(xhrViewResponse.view, getUrl());
      List<HtmlElement> userlist = document.find("ul.userlist > li");

      collections = unmodifiableList(newArrayList(transform(userlist, favoritesToWallpaperCollectionTransformer)));

      return collections;
   }

   private static class XhrViewResponse
   {
      public String view;
   }
}
