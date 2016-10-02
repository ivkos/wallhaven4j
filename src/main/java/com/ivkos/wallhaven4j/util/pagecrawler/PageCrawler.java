package com.ivkos.wallhaven4j.util.pagecrawler;

import java.util.List;
import java.util.Map;

public interface PageCrawler<T>
{
   String getUrl();

   List<T> getPage(long pageNumber, Map<String, String> queryParams);

   List<T> getPageSequence(long pageCount, Map<String, String> queryParams);
}
