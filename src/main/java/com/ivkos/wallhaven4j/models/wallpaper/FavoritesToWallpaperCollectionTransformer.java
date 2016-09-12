package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionFactory;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionIdentifier;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector.of;
import static java.lang.Long.parseLong;
import static java.lang.String.format;


class FavoritesToWallpaperCollectionTransformer implements Function<HtmlElement, WallpaperCollection>
{
   private final ResourceFactoryFactory rff;

   @Inject
   FavoritesToWallpaperCollectionTransformer(ResourceFactoryFactory rff)
   {
      this.rff = rff;
   }

   @Override
   public WallpaperCollection apply(HtmlElement input)
   {
      String username = of(input, "a.username")
            .orElseThrow(new ParseException("Could not get username for wallpaper collection"))
            .getText();

      User user = rff.getFactoryFor(User.class).create(false, username);

      HtmlElement lastLink = input.findLast("a");
      if (lastLink == null)
         throw new ParseException("Could not parse wallpaper collection, no URL of collection");

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
}
