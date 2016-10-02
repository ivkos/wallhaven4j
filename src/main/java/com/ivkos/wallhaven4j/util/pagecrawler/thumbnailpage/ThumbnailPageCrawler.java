package com.ivkos.wallhaven4j.util.pagecrawler.thumbnailpage;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.exceptions.WallhavenException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlParser;
import com.ivkos.wallhaven4j.util.httpclient.HttpClient;
import com.ivkos.wallhaven4j.util.httpclient.HttpResponse;
import com.ivkos.wallhaven4j.util.pagecrawler.PageCrawler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.X_REQUESTED_WITH;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static java.util.Collections.unmodifiableList;

public class ThumbnailPageCrawler implements PageCrawler<Wallpaper>
{
   private final ThumbnailTransformer thumbnailTransformer;
   private final HttpClient httpClient;
   private final HtmlParser htmlParser;

   private final String url;

   @AssistedInject
   ThumbnailPageCrawler(ThumbnailTransformer thumbnailTransformer,
                        HttpClient httpClient,
                        HtmlParser htmlParser,
                        @Assisted String url)
   {
      this.thumbnailTransformer = thumbnailTransformer;
      this.httpClient = httpClient;
      this.htmlParser = htmlParser;

      this.url = url;
   }

   @Override
   public String getUrl()
   {
      return url;
   }

   @Override
   public List<Wallpaper> getPage(long pageNumber, Map<String, String> queryParams)
   {
      checkArgument(pageNumber > 0, "page number must be greater than zero");

      ImmutableMap<String, String> params = ImmutableMap.<String, String>builder()
            .put("page", Long.toString(pageNumber))
            .putAll(queryParams)
            .build();

      HttpResponse response = httpClient.get(getUrl(), ImmutableMap.of(
            X_REQUESTED_WITH, "XMLHttpRequest",
            CONTENT_TYPE, JSON_UTF_8.toString()
      ), params);

      HtmlElement dom = htmlParser.parse(response.getBody());

      List<HtmlElement> figureElements = dom.find("li > figure");

      return unmodifiableList(transform(figureElements, thumbnailTransformer::transform));
   }

   @Override
   public List<Wallpaper> getPageSequence(long pageCount, Map<String, String> queryParams)
   {
      checkArgument(pageCount > 0, "page count must be greater than zero");

      ExecutorService executorService = Executors.newCachedThreadPool();

      List<Future<List<Wallpaper>>> futures = newArrayList();
      for (long page = 1; page <= pageCount; page++) {
         long finalPage = page;
         futures.add(executorService.submit(() -> getPage(finalPage, queryParams)));
      }

      List<Wallpaper> wallpapers = newArrayList();
      for (Future<List<Wallpaper>> future : futures) {
         try {
            wallpapers.addAll(future.get());
         } catch (InterruptedException e) {
            throw new WallhavenException("Could not get wallpapers in page", e);
         } catch (ExecutionException e) {
            throw new WallhavenException("Could not get wallpapers in page", e.getCause());
         }
      }

      executorService.shutdown();

      return unmodifiableList(wallpapers);
   }
}
