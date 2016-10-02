package com.ivkos.wallhaven4j.util.pagecrawler.thumbnailpage;

import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import com.ivkos.wallhaven4j.util.pagecrawler.PageCrawlerFactory;

public interface ThumbnailPageCrawlerFactory extends PageCrawlerFactory<Wallpaper>
{
   @Override
   ThumbnailPageCrawler create(String url);
}
