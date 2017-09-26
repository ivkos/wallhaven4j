package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.misc.Color;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.htmlparser.TimeElementParser;
import com.ivkos.wallhaven4j.util.jsonserializer.JsonSerializer;
import org.joda.time.DateTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
   private final ResourceFactoryFactory rff;
   private final WallpaperTransformers transformers;

   private Resolution resolution;
   private List<Color> colors;
   private List<Tag> tags;
   private Purity purity;
   private User user;
   private DateTime dateCreated;
   private Category category;
   private Double size;
   private Long viewsCount;

   private Long favoritesCount;
   private List<WallpaperCollection> collections;

   private String imageUrl;

   @AssistedInject
   Wallpaper(WallhavenSession session,
             JsonSerializer jsonSerializer,
             ResourceFactoryFactory resourceFactoryFactory,
             WallpaperTransformers wallpaperTransformers,
             @Assisted boolean preloadDom,
             @Assisted long id)
   {
      super(session, preloadDom, id);

      this.jsonSerializer = jsonSerializer;
      this.rff = resourceFactoryFactory;
      this.transformers = wallpaperTransformers;

      if (preloadDom) populateFields();
   }

   @AssistedInject
   Wallpaper(WallhavenSession session,
             JsonSerializer jsonSerializer,
             ResourceFactoryFactory resourceFactoryFactory,
             WallpaperTransformers wallpaperTransformers,
             @Assisted boolean preloadDom,
             @Assisted("id") long id,
             @Assisted Category category,
             @Assisted Purity purity,
             @Assisted Resolution resolution,
             @Assisted("favoritesCount") long favoritesCount)
   {
      super(session, preloadDom, id);

      this.jsonSerializer = jsonSerializer;
      this.rff = resourceFactoryFactory;
      this.transformers = wallpaperTransformers;

      this.category = category;
      this.purity = purity;
      this.resolution = resolution;
      this.favoritesCount = favoritesCount;

      if (preloadDom) populateFields();
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_WALLPAPER + "/" + id;
   }

   /**
    * Returns the short link of the wallpaper
    *
    * @return the short link of the wallpaper
    */
   public String getShortLink()
   {
      return UrlPrefixes.URL_WALLPAPER_SHORT + "/" + id;
   }

   /**
    * Returns the resolution of the wallpaper
    *
    * @return the resolution of the wallpaper
    */
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

   /**
    * Returns a list of the dominant colors in the wallpaper
    *
    * @return a list of the dominant colors in the wallpaper
    */
   @ResourceFieldGetter
   public List<Color> getColors()
   {
      if (colors != null) {
         return colors;
      }

      List<HtmlElement> colorElements = getDom().find("ul.color-palette > li.color");

      colors = unmodifiableList(newArrayList(transform(colorElements, transformers::transformToColor)));

      return colors;
   }

   /**
    * Returns a list of the tags the wallpaper has been tagged with
    *
    * @return a list of the tags the wallpaper has been tagged with
    */
   @ResourceFieldGetter
   public List<Tag> getTags()
   {
      if (tags != null) return tags;

      List<HtmlElement> tagElements = getDom().find("ul#tags > li.tag");

      tags = unmodifiableList(newArrayList(transform(tagElements, transformers::transformToTag)));

      return tags;
   }

   /**
    * Returns the purity of the wallpaper
    *
    * @return the purity of the wallpaper
    */
   @ResourceFieldGetter
   public Purity getPurity()
   {
      if (purity != null) return purity;

      HtmlElement purityRadio = of(getDom(), "#wallpaper-purity-form > input[type=radio][checked]").get();
      HtmlElement purityLabel = purityRadio.getNextElementSibling();

      if (purityLabel == null) {
         throw new ParseException("Could not parse purity of wallpaper (could not find sibling of purity radio element)");
      }

      purity = purityLabel.hasClass("sfw") ? SFW
            : purityLabel.hasClass("sketchy") ? SKETCHY
            : purityLabel.hasClass("nsfw") ? NSFW
            : null;

      if (purity == null) throw new ParseException("Could not parse purity of wallpaper");

      return purity;
   }

   /**
    * Returns the user who has uploaded the wallpaper
    *
    * @return the user who has uploaded the wallpaper
    */
   @ResourceFieldGetter
   public User getUser()
   {
      if (user != null) return user;

      HtmlElement uploaderElement = of(getDom().findElementById("showcase-sidebar"), "dd.showcase-uploader > a.username").get();

      String username = uploaderElement.getText();

      user = rff.getFactoryFor(User.class).create(false, username);

      return user;
   }

   /**
    * Returns the date and time the wallpaper has been uploaded
    *
    * @return the date and time the wallpaper has been uploaded
    */
   @ResourceFieldGetter
   public DateTime getDateCreated()
   {
      if (dateCreated != null) return dateCreated;

      HtmlElement timeElement = of(getDom().findElementById("showcase-sidebar"), "dd.showcase-uploader > time").get();

      dateCreated = TimeElementParser.parse(timeElement);

      return dateCreated;
   }

   /**
    * Returns the category of the wallpaper
    *
    * @return the category of the wallpaper
    */
   @ResourceFieldGetter
   public Category getCategory()
   {
      if (category != null) return category;

      String categoryText = of(getElementNextToLabel("Category"), "dd")
            .get()
            .getText();

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
    * Returns the size of the wallpaper in KiB
    *
    * @return the size of the wallpaper in KiB
    */
   @ResourceFieldGetter
   public Double getSize()
   {
      if (size != null) return size;

      String sizeText = of(getElementNextToLabel("Size"), "dd")
            .get()
            .getText();

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

   /**
    * Returns the number of views the wallpaper has gotten
    *
    * @return the number of views the wallpaper has gotten
    */
   @ResourceFieldGetter
   public Long getViewsCount()
   {
      if (viewsCount != null) return viewsCount;

      String viewsString = of(getElementNextToLabel("Views"), "dd")
            .get()
            .getText()
            .trim()
            .replace(",", "");


      try {
         viewsCount = parseLong(viewsString);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse views count", e);
      }

      return viewsCount;
   }

   /**
    * Returns the number of collections the wallpaper has been added to
    *
    * @return the number of collections the wallpaper has been added to
    */
   @ResourceFieldGetter
   public Long getFavoritesCount()
   {
      if (favoritesCount != null) return favoritesCount;

      String favoritesCountString = of(getElementNextToLabel("Favorites"), "dd")
            .get()
            .getText()
            .trim()
            .replace(",", "");


      try {
         favoritesCount = parseLong(favoritesCountString);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse favorites count", e);
      }

      return favoritesCount;
   }

   /**
    * Returns a list of collections the wallpaper has been added to
    *
    * @return a list of collections the wallpaper has been added to
    */
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

      collections = unmodifiableList(newArrayList(transform(userlist,
            transformers::transformToWallpaperCollection)));

      return collections;
   }

   public String getThumbnailUrl()
   {
      return UrlPrefixes.URL_IMAGE_THUMB + getId() + ".jpg";
   }

   @ResourceFieldGetter
   public String getImageUrl()
   {
      if (imageUrl != null) return imageUrl;

      String schemalessUrl = OptionalSelector.of(getDom(), "img#wallpaper")
            .orElseThrow(DescriptiveParseExceptionSupplier.forResource(this, "image URL"))
            .getAttribute("src");

      URI uri;
      try {
         uri = new URI("https", schemalessUrl, null);
      } catch (URISyntaxException e) {
         throw DescriptiveParseExceptionSupplier.forResource(this, "image URL").get(e);
      }

      imageUrl = uri.toString();

      return imageUrl;
   }

   private HtmlElement getElementNextToLabel(String labelContent)
   {
      List<HtmlElement> dts = getDom().find("div.sidebar-content dl > dt");

      ArrayList<HtmlElement> list = newArrayList(Collections2.filter(dts, e -> labelContent.equals(e.getText())));

      if (list.isEmpty()) return null;

      return list.get(0).getNextElementSibling();
   }

   private static class XhrViewResponse
   {
      public String view;
   }
}
