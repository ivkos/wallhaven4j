package com.ivkos.wallhaven4j.util.htmlparser;

import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.jsoup.JsoupHtmlParser;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OptionalSelectorTest
{
   private static HtmlElement dom;
   private static OptionalSelector legitSelector;

   @BeforeClass
   public static void setUp() throws Exception
   {
      HtmlParser parser = new JsoupHtmlParser();

      dom = parser.parse("<body><div id=\"id1\"></div></body>");
      assertNotNull(dom);

      legitSelector = OptionalSelector.of(dom, "#id1");
      assertNotNull(legitSelector);
   }

   @Test
   public void getWorks() throws Exception
   {
      HtmlElement htmlElement = legitSelector.get();

      assertNotNull(htmlElement);
      assertEquals("div", htmlElement.getTagName());
   }

   @Test
   public void itDoesNotThrow() throws Exception
   {
      HtmlElement htmlElement = legitSelector.orElseThrow(new Exception());

      assertNotNull(htmlElement);
      assertEquals("div", htmlElement.getTagName());
   }

   @Test(expected = IllegalArgumentException.class)
   public void itThrowsTheSpecifiedException() throws Exception
   {
      OptionalSelector.of(dom, ".nope").orElseThrow(new IllegalArgumentException());
   }

   @Test(expected = ParseException.class)
   public void itThrowsParseException() throws Exception
   {
      OptionalSelector.of(dom, ".nope").get();
   }

}
