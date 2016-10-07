package com.ivkos.wallhaven4j;

import com.google.common.base.Strings;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.tag.Tag;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollection;
import com.ivkos.wallhaven4j.models.wallpapercollection.WallpaperCollectionIdentifier;
import com.ivkos.wallhaven4j.util.UrlPrefixes;
import com.ivkos.wallhaven4j.util.WallhavenGuiceModule;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.LoginException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;
import com.ivkos.wallhaven4j.util.pagecrawler.thumbnailpage.ThumbnailPageCrawlerFactory;
import com.ivkos.wallhaven4j.util.searchquery.SearchQuery;

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Wallhaven
{
   private final WallhavenSession session;
   private final ResourceFactoryFactory rff;
   private final ThumbnailPageCrawlerFactory thumbnailPageCrawlerFactory;

   /**
    * Creates a new anonymous Wallhaven session.
    *
    * @param cookiesFile file to save cookies in
    */
   public Wallhaven(File cookiesFile)
   {
      Injector injector = Guice.createInjector(new WallhavenGuiceModule(cookiesFile));

      session = injector.getInstance(WallhavenSession.class);
      rff = injector.getInstance(ResourceFactoryFactory.class);
      thumbnailPageCrawlerFactory = injector.getInstance(ThumbnailPageCrawlerFactory.class);
   }

   /**
    * Creates a new Wallhaven session and logs in with the specified credentials.
    *
    * @param username    the username
    * @param password    the password
    * @param cookiesFile file to save cookies in
    * @throws LoginException if the credentials are incorrect
    */
   public Wallhaven(String username, String password, File cookiesFile)
   {
      this(cookiesFile);

      session.login(username, password);
   }

   /**
    * Creates a new anonymous Wallhaven session.
    */
   public Wallhaven()
   {
      this(null);
   }

   /**
    * Creates a new Wallhaven session and logs in with the specified credentials.
    *
    * @param username the username
    * @param password the password
    * @throws LoginException if the credentials are incorrect
    */
   public Wallhaven(String username, String password)
   {
      this(username, password, null);
   }

   /**
    * Returns the currently logged in user, or <code>null</code> if the session is anonymous.
    *
    * @return the currently logged in user, or <code>null</code> if the session is anonymous.
    */
   public User getCurrentUser()
   {
      return session.getCurrentUser();
   }

   /**
    * Finds the tag with the specified id and returns a {@link Tag} object that represents it.
    *
    * @param id the tag's id
    * @return the tag
    * @throws ResourceNotFoundException if there is no tag with the specified id
    * @throws IllegalArgumentException  if the specified id is not greater than zero
    */
   public Tag getTag(long id)
   {
      checkArgument(id > 0, "id must be greater than zero");

      return rff.getFactoryFor(Tag.class).create(true, id);
   }

   /**
    * Finds the user with the specified username and returns a {@link User} object that represents it.
    *
    * @param username the username
    * @return the tag
    * @throws ResourceNotFoundException if there is no user with the specified username
    * @throws IllegalArgumentException  if the specified username is <code>null</code> or empty
    */
   public User getUser(String username)
   {
      checkArgument(!Strings.isNullOrEmpty(username), "username must not be null or empty");

      return rff.getFactoryFor(User.class).create(true, username);
   }

   /**
    * Finds the wallpaper with the specified id and returns a {@link Wallpaper} object that represents it.
    *
    * @param id the wallpaper's id
    * @return the wallpaper
    * @throws ResourceNotFoundException      if there is no wallpaper with the specified id
    * @throws ResourceNotAccessibleException if the wallpaper is NSFW but the current Wallhaven session is anonymous
    * @throws IllegalArgumentException       if the specified id is not greater than zero
    */
   public Wallpaper getWallpaper(long id)
   {
      checkArgument(id > 0, "id must be greater than zero");

      return rff.getFactoryFor(Wallpaper.class).create(true, id);
   }

   /**
    * Finds the wallpaper collection with the specified id owned by the specified user
    * and returns a {@link WallpaperCollection} object that represents it.
    *
    * @param username the username of the user that owns the wallpaper collection
    * @param id       the wallpaper collection id
    * @return the wallpaper collection
    * @throws ResourceNotFoundException if there is no such wallpaper collection
    * @throws IllegalArgumentException  if the username is <code>null</code> or empty, or the id is not greater than zero
    */
   public WallpaperCollection getWallpaperCollection(String username, long id)
   {
      checkArgument(!Strings.isNullOrEmpty(username), "username must not be null or empty");
      checkArgument(id > 0, "id must be greater than zero");

      User user = rff.getFactoryFor(User.class).create(false, username);

      return rff.getFactoryFor(WallpaperCollection.class).create(true, new WallpaperCollectionIdentifier(id, user));
   }

   /**
    * Executes the search query and returns a list of wallpapers matching the query
    *
    * @param searchQuery the search query
    * @return a list of wallpapers matching the query
    * @throws NullPointerException if searchQuery is null
    */
   public List<Wallpaper> search(SearchQuery searchQuery)
   {
      checkNotNull(searchQuery, "searchQuery must not be null");

      return thumbnailPageCrawlerFactory
            .create(UrlPrefixes.URL_SEARCH)
            .getPageSequence(
                  searchQuery.getPages(),
                  searchQuery.getQueryParamsMap()
            );
   }

   /**
    * Executes the search query and returns a list of the wallpapers in the specified page
    *
    * @param searchQuery the search query
    * @param page        the page to fetch
    * @return a list of wallpapers matching the query
    * @throws IllegalArgumentException if page is not greater than zero
    * @throws NullPointerException     if searchQuery is null
    */
   public List<Wallpaper> search(SearchQuery searchQuery, long page)
   {
      checkNotNull(searchQuery, "searchQuery must not be null");
      checkArgument(page > 0, "page must be greater than zero");

      return thumbnailPageCrawlerFactory
            .create(UrlPrefixes.URL_SEARCH)
            .getPage(page, searchQuery.getQueryParamsMap());
   }
}
