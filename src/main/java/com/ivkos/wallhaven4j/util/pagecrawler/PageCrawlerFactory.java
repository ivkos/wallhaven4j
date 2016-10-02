package com.ivkos.wallhaven4j.util.pagecrawler;

public interface PageCrawlerFactory<T>
{
   PageCrawler<T> create(String pageUrl);
}
