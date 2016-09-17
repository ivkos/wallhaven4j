package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.google.inject.Inject;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;

import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.of;

class WallpaperCollectionTransformers
{
   private final ResourceFactoryFactory rff;

   @Inject
   WallpaperCollectionTransformers(ResourceFactoryFactory rff)
   {
      this.rff = rff;
   }

   public User transformSubscribers(HtmlElement input)
   {
      String username = OptionalSelector.of(input, "a.username")
            .orElseThrowSupplied(of(WallpaperCollection.class, "subscribed user"))
            .getText()
            .trim();

      return rff.getFactoryFor(User.class).create(false, username);
   }
}
