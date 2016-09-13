package com.ivkos.wallhaven4j.models.tag;


import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.AbstractResource;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategory;
import com.ivkos.wallhaven4j.models.tagcategory.TagCategoryFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.htmlparser.TimeElementParser;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.transform;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.*;
import static java.lang.Long.parseLong;
import static java.lang.String.format;

public class Tag extends AbstractResource<Long>
{
   private final ResourceFactoryFactory rff;

   private String name;
   private Purity purity;
   private TagCategory category;
   private Set<String> aliases;

   private Long taggedWallpapersCount;
   private Long taggedWallpapersViewCount;
   private Long subscribersCount;

   private User user;
   private DateTime dateCreated;

   @AssistedInject
   Tag(WallhavenSession session,
       ResourceFactoryFactory rff,
       @Assisted boolean preloadDom,
       @Assisted long id)
   {
      super(session, preloadDom, id);
      this.rff = rff;

      if (preloadDom) populateFields();
   }

   @AssistedInject
   Tag(WallhavenSession session,
       ResourceFactoryFactory rff,
       @Assisted boolean preloadDom,
       @Assisted long id,
       @Assisted String name,
       @Assisted Purity purity)
   {
      super(session, preloadDom, id);
      this.rff = rff;
      this.name = name;
      this.purity = purity;

      if (preloadDom) populateFields();
   }

   @Override
   public String toString()
   {
      return "#" + name;
   }

   @Override
   public String getUrl()
   {
      return UrlPrefixes.URL_TAG + "/" + id;
   }

   @ResourceFieldGetter
   public String getName()
   {
      if (this.name != null) return this.name;

      this.name = OptionalSelector.of(getDom(), "#tag > h1.tagname").get().getText();

      return this.name;
   }

   @ResourceFieldGetter
   public Purity getPurity()
   {
      if (this.purity != null) return this.purity;

      HtmlElement element = OptionalSelector.of(getDom(), "#tag > h1.tagname").get();

      this.purity = element.hasClass("sfw") ? SFW
            : element.hasClass("sketchy") ? SKETCHY
            : element.hasClass("nsfw") ? NSFW
            : null;

      if (purity == null) throw new ParseException("Could not parse purity of tag");

      return this.purity;
   }

   @ResourceFieldGetter
   public TagCategory getCategory()
   {
      if (this.category != null) return this.category;

      List<HtmlElement> elements = getDom().find("h2.tag-categories > a");

      if (elements.isEmpty()) throw new ParseException("Could not parse tag category");

      final TagCategoryFactory tcf = rff.getFactoryFor(TagCategory.class);
      List<TagCategory> categories = transform(elements, input -> {
         String href = input.getAttribute("href");

         Pattern pattern = Pattern.compile(Pattern.quote(UrlPrefixes.URL_TAGS + "/") + "(\\d+)");
         Matcher matcher = pattern.matcher(href);

         if (!matcher.matches()) {
            throw new ParseException(
                  format(
                        "Could not parse tag category URL\nFound URL %s does not match pattern %s",
                        href,
                        pattern.toString()
                  )
            );
         }

         long id1 = parseLong(matcher.group(1));
         String name1 = input.getText();

         return tcf.create(false, id1, name1);
      });

      TagCategory top = null;
      for (TagCategory current : categories) {
         if (top == null) {
            top = tcf.create(false, current.getId(), current.getName());
         } else {
            top = tcf.create(false, current.getId(), current.getName(), top);
         }
      }

      this.category = top;

      return this.category;
   }

   @ResourceFieldGetter
   public Set<String> getAliases()
   {
      if (this.aliases != null) return this.aliases;

      HtmlElement element = OptionalSelector.of(getDom(), "#tag > h1.tagname").get();
      String originalTitle = element.getAttribute("title");

      Pattern pattern = Pattern.compile("^" + Pattern.quote("Aliases: ") + "((.+)(,\\s.+)*)$");
      Matcher matcher = pattern.matcher(originalTitle);

      if (!matcher.matches()) {
         throw new ParseException("Could not parse tag aliases");
      }

      String group = matcher.group(1);
      String[] split = group.split(",\\s");

      this.aliases = ImmutableSet.copyOf(split);

      return this.aliases;
   }

   @ResourceFieldGetter
   public Long getTaggedWallpapersCount()
   {
      if (taggedWallpapersCount != null) return taggedWallpapersCount;

      String text = OptionalSelector.of(getDom(), "#tag-info > dl > dd:nth-child(2)")
            .get()
            .getText()
            .replace(",", "");

      try {
         this.taggedWallpapersCount = parseLong(text);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse tagged wallpapers count", e);
      }

      return taggedWallpapersCount;
   }

   @ResourceFieldGetter
   public Long getTaggedWallpapersViewCount()
   {
      if (taggedWallpapersViewCount != null) return taggedWallpapersViewCount;


      String text = OptionalSelector.of(getDom(), "#tag-info > dl > dd:nth-child(4)")
            .get()
            .getText()
            .replace(",", "");

      try {
         this.taggedWallpapersViewCount = parseLong(text);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse tagged wallpapers views count", e);
      }

      return taggedWallpapersViewCount;
   }

   @ResourceFieldGetter
   public Long getSubscribersCount()
   {
      if (subscribersCount != null) return subscribersCount;

      String text = OptionalSelector.of(getDom(), "#tag-info > dl > dd:nth-child(6)")
            .get()
            .getText()
            .replace(",", "");

      try {
         this.subscribersCount = parseLong(text);
      } catch (NumberFormatException e) {
         throw new ParseException("Could not parse subscribers count", e);
      }

      return subscribersCount;
   }

   @ResourceFieldGetter
   public User getUser()
   {
      if (user != null) return user;

      String username = OptionalSelector
            .of(getDom(), "#tag-info > dl > dd:nth-child(8) > a.username")
            .get()
            .getText();

      this.user = rff.getFactoryFor(User.class).create(false, username);

      return user;
   }

   @ResourceFieldGetter
   public DateTime getDateCreated()
   {
      if (dateCreated != null) return dateCreated;

      HtmlElement timeElement = OptionalSelector.of(getDom(), "#tag-info > dl > dd:nth-child(8) > time").get();

      dateCreated = TimeElementParser.parse(timeElement);

      return dateCreated;
   }
}
