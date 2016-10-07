package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;

import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.forResource;

@Singleton
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
            .orElseThrow(forResource(WallpaperCollection.class, "subscribed user"))
            .getText()
            .trim();

      return rff.getFactoryFor(User.class).create(false, username);
   }
}
