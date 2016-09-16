package com.ivkos.wallhaven4j.util.htmlparser;

import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TimeElementParserTest
{
   @Test(expected = IllegalArgumentException.class)
   public void parseWrongElement() throws Exception
   {
      HtmlElement div = new HtmlElement()
      {
         @Override
         public List<HtmlElement> find(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findFirst(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findLast(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findElementById(String id)
         {
            return null;
         }

         @Override
         public HtmlElement getNextElementSibling()
         {
            return null;
         }

         @Override
         public String getText()
         {
            return null;
         }

         @Override
         public String getAttribute(String attributeName)
         {
            return null;
         }

         @Override
         public boolean hasClass(String className)
         {
            return false;
         }

         @Override
         public Map<String, String> getDataAttributes()
         {
            return null;
         }

         @Override
         public String getValue()
         {
            return null;
         }

         @Override
         public String getTagName()
         {
            return "div";
         }
      };

      TimeElementParser.parse(div);
   }

   @Test(expected = ParseException.class)
   public void parseEmptyDateTime() throws Exception
   {
      HtmlElement div = new HtmlElement()
      {
         @Override
         public List<HtmlElement> find(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findFirst(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findLast(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findElementById(String id)
         {
            return null;
         }

         @Override
         public HtmlElement getNextElementSibling()
         {
            return null;
         }

         @Override
         public String getText()
         {
            return null;
         }

         @Override
         public String getAttribute(String attributeName)
         {
            return "";
         }

         @Override
         public boolean hasClass(String className)
         {
            return false;
         }

         @Override
         public Map<String, String> getDataAttributes()
         {
            return null;
         }

         @Override
         public String getValue()
         {
            return null;
         }

         @Override
         public String getTagName()
         {
            return "time";
         }
      };

      TimeElementParser.parse(div);
   }

   @Test(expected = ParseException.class)
   public void parseIllegalDatetime() throws Exception
   {
      HtmlElement div = new HtmlElement()
      {
         @Override
         public List<HtmlElement> find(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findFirst(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findLast(String cssQuery)
         {
            return null;
         }

         @Override
         public HtmlElement findElementById(String id)
         {
            return null;
         }

         @Override
         public HtmlElement getNextElementSibling()
         {
            return null;
         }

         @Override
         public String getText()
         {
            return null;
         }

         @Override
         public String getAttribute(String attributeName)
         {
            return "a1b2c3";
         }

         @Override
         public boolean hasClass(String className)
         {
            return false;
         }

         @Override
         public Map<String, String> getDataAttributes()
         {
            return null;
         }

         @Override
         public String getValue()
         {
            return null;
         }

         @Override
         public String getTagName()
         {
            return "time";
         }
      };

      TimeElementParser.parse(div);
   }
}
