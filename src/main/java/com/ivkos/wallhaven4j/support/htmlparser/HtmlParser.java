package com.ivkos.wallhaven4j.support.htmlparser;

public interface HtmlParser
{
   HtmlElement parse(String html);

   HtmlElement parse(String html, String baseUrl);
}
