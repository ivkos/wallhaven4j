package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.misc.Color;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.tag.TagFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionIdentifier;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;
import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.forResource;
import static com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector.of;
import static java.lang.Long.parseLong;
import static java.lang.String.format;


@Singleton
class WallpaperTransformers
{
   private final ResourceFactoryFactory rff;

   @Inject
   WallpaperTransformers(ResourceFactoryFactory rff)
   {
      this.rff = rff;
   }

   WallpaperCollection transformToWallpaperCollection(HtmlElement input)
   {
      String username = of(input, "a.username")
            .orElseThrow(forResource(WallpaperCollection.class, "username"))
            .getText();

      User user = rff.getFactoryFor(User.class).create(false, username);

      HtmlElement lastLink = input.findLast("a");
      if (lastLink == null)
         throw forResource(WallpaperCollection.class, "URL of collection").get();

      String name = lastLink.getText();
      String href = lastLink.getAttribute("href");
      Pattern pattern = Pattern.compile(Pattern.quote(user.getUrl()) + "/favorites/(\\d+)");
      Matcher matcher = pattern.matcher(href);

      if (!matcher.matches())
         throw new ParseException(
               format("Could not parse wallpaper collection URL.\nFound URL %s does not match pattern %s",
                     href, pattern.toString()));

      long id = parseLong(matcher.group(1));

      return ((WallpaperCollectionFactory) rff.getFactoryFor(WallpaperCollection.class)).create(
            false,
            new WallpaperCollectionIdentifier(id, user),
            name
      );
   }

   Tag transformToTag(HtmlElement input)
   {
      long tagId = parseLong(input.getDataAttributes().get("tag-id"));
      String tagName = input.findFirst("a.tagname").getText();

      Purity tagPurity = input.hasClass("tag-sfw") ? SFW
            : input.hasClass("tag-sketchy") ? SKETCHY
            : input.hasClass("tag-nsfw") ? NSFW
            : null;

      if (tagPurity == null) throw new ParseException("Could not parse purity of tag");

      return ((TagFactory) rff.getFactoryFor(Tag.class)).create(false, tagId, tagName, tagPurity);
   }

   Color transformToColor(HtmlElement input)
   {
      Pattern PATTERN_COLOR = Pattern.compile("background-color:(#[0-9A-Fa-f]{6})");
      Matcher matcher = PATTERN_COLOR.matcher(input.getAttribute("style"));

      if (!matcher.matches()) throw new ParseException("Could not parse color");

      String hex = matcher.group(1);

      return new Color(hex);
   }
}
