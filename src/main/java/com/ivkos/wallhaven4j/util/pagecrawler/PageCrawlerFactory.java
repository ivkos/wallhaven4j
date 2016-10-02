package com.ivkos.wallhaven4j.util.pagecrawler;

public interface PageCrawlerFactory<C extends PageCrawler<?>>
{
   C create(String pageUrl);
}
