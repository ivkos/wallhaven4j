package com.ivkos.wallhaven4j.util.htmlparser;

import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.jsoup.JsoupHtmlParser;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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
      HtmlElement htmlElement = legitSelector.orElseThrow(Exception::new);

      assertNotNull(htmlElement);
      assertEquals("div", htmlElement.getTagName());
   }

   @Test(expected = IllegalArgumentException.class)
   public void itThrowsTheSpecifiedException() throws Exception
   {
      OptionalSelector.of(dom, ".nope").orElseThrow(IllegalArgumentException::new);
   }

   @Test(expected = ParseException.class)
   public void itThrowsParseException() throws Exception
   {
      OptionalSelector.of(dom, ".nope").get();
   }

   @Test
   public void isPresent() throws Exception
   {
      assertTrue(legitSelector.isPresent());
      assertFalse(OptionalSelector.of(dom, "#nope").isPresent());
   }
}
