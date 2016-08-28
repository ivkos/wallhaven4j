package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.user.UserFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionIdentifier;
import com.ivkos.wallhaven4j.support.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ivkos.wallhaven4j.support.optionalselector.OptionalSelector.of;
import static java.lang.Long.parseLong;
import static java.lang.String.format;


class FavoritesToWallpaperCollectionTransformer implements Function<Element, WallpaperCollection>
{
   private final UserFactory userFactory;
   private final WallpaperCollectionFactory wallpaperCollectionFactory;

   @Inject
   public FavoritesToWallpaperCollectionTransformer(UserFactory userFactory, WallpaperCollectionFactory wallpaperCollectionFactory)
   {
      this.userFactory = userFactory;
      this.wallpaperCollectionFactory = wallpaperCollectionFactory;
   }

   @Override
   public WallpaperCollection apply(Element input)
   {
      String username = of(input, "a.username")
            .orElseThrow(new ParseException("Could not get username for wallpaper collection"))
            .text();

      User user = userFactory.create(false, username);

      Element lastLink = input.select("a").last();
      if (lastLink == null)
         throw new ParseException("Could not parse wallpaper collection, no URL of collection");

      String name = lastLink.text();
      String href = lastLink.attr("href");
      Pattern pattern = Pattern.compile(Pattern.quote(user.getUrl()) + "/favorites/(\\d+)");
      Matcher matcher = pattern.matcher(href);

      if (!matcher.matches())
         throw new ParseException(
               format("Could not parse wallpaper collection URL.\nFound URL %s does not match pattern %s",
                     href, pattern.toString()));

      long id = parseLong(matcher.group(1));

      return wallpaperCollectionFactory.create(
            false,
            new WallpaperCollectionIdentifier(id, user),
            name
      );
   }
}
