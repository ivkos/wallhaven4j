package com.ivkos.wallhaven4j.util.pagecrawler;

import com.ivkos.wallhaven4j.models.AbstractResource;

import java.util.List;
import java.util.Map;

public interface PageCrawler<R extends AbstractResource<?>>
{
   String getUrl();

   List<R> getPage(long pageNumber, Map<String, String> queryParams);

   List<R> getPageSequence(long pageCount, Map<String, String> queryParams);
}
