package com.ivkos.wallhaven4j.util.htmlparser;

public interface HtmlParser
{
   HtmlElement parse(String html);

   HtmlElement parse(String html, String baseUrl);
}
