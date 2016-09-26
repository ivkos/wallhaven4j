package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.inject.Inject;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;

import static com.ivkos.wallhaven4j.models.misc.enums.Category.ANIME;
import static com.ivkos.wallhaven4j.models.misc.enums.Category.GENERAL;
import static com.ivkos.wallhaven4j.models.misc.enums.Category.PEOPLE;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.NSFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SKETCHY;
import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.forResource;
import static java.lang.Long.parseLong;

public class ThumbnailTransformer
{
   private final ResourceFactoryFactory rff;

   @Inject
   ThumbnailTransformer(ResourceFactoryFactory rff)
   {
      this.rff = rff;
   }

   public Wallpaper transform(HtmlElement figureElement)
   {
      String idText = figureElement.getDataAttributes().get("wallpaper-id");

      //region id
      long id;
      try {
         id = parseLong(idText);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse wallpaper id of wallpaper thumbnail");
      }
      //endregion

      //region category
      Category category = figureElement.hasClass("thumb-general") ? GENERAL
            : figureElement.hasClass("thumb-anime") ? ANIME
            : figureElement.hasClass("thumb-people") ? PEOPLE
            : null;

      if (category == null) throw new ParseException("Could not parse category of wallpaper thumbnail");
      //endregion

      //region purity
      Purity purity = figureElement.hasClass("thumb-sfw") ? SFW
            : figureElement.hasClass("thumb-sketchy") ? SKETCHY
            : figureElement.hasClass("thumb-nsfw") ? NSFW
            : null;

      if (purity == null) throw new ParseException("Could not parse purity of wallpaper thumbnail");
      //endregion

      //region resolution
      HtmlElement resolutionElement = OptionalSelector.of(figureElement, "span.wall-res")
            .orElseThrowSupplied(forResource(Wallpaper.class, "resolution in thumbnail"));

      String resolutionText = resolutionElement.getText().replace(" ", "");
      Resolution resolution = Resolution.parse(resolutionText);
      //endregion

      //region favorites count
      String favoritesCountText = OptionalSelector.of(figureElement, "a.wall-favs")
            .orElseThrowSupplied(forResource(Wallpaper.class, "favorites count in thumbnail"))
            .getText()
            .trim()
            .replace(",", "");

      long favoritesCount = parseLong(favoritesCountText);
      //endregion

      WallpaperFactory wallpaperFactory = rff.getFactoryFor(Wallpaper.class);
      wallpaperFactory.create(false, id, category, purity, resolution, favoritesCount);

      return wallpaperFactory.create(false, id);
   }
}
