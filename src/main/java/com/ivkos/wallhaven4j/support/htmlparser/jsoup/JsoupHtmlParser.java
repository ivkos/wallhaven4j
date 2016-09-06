package com.ivkos.wallhaven4j.support.htmlparser.jsoup;

import com.ivkos.wallhaven4j.support.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.support.htmlparser.HtmlParser;
import org.jsoup.Jsoup;

public class JsoupHtmlParser implements HtmlParser
{
   @Override
   public HtmlElement parse(String html)
   {
      return new JsoupHtmlElement(Jsoup.parse(html));
   }

   @Override
   public HtmlElement parse(String html, String baseUrl)
   {
      return new JsoupHtmlElement(Jsoup.parse(html, baseUrl));
   }
}
