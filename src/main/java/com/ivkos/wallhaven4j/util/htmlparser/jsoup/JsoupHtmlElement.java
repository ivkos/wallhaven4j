package com.ivkos.wallhaven4j.util.htmlparser.jsoup;

import com.google.common.base.Function;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.transform;

public class JsoupHtmlElement implements HtmlElement
{
   private final Element jsoupElement;

   public JsoupHtmlElement(Element jsoupElement)
   {
      this.jsoupElement = jsoupElement;
   }

   @Override
   public List<HtmlElement> find(String cssQuery)
   {
      return Collections.unmodifiableList(transform(jsoupElement.select(cssQuery), new Function<Element, HtmlElement>()
      {
         @Override
         public HtmlElement apply(Element input)
         {
            return new JsoupHtmlElement(input);
         }
      }));
   }

   @Override
   public HtmlElement findFirst(String cssQuery)
   {
      List<HtmlElement> result = find(cssQuery);

      return result.isEmpty() ? null : result.get(0);
   }

   @Override
   public HtmlElement findLast(String cssQuery)
   {
      List<HtmlElement> result = find(cssQuery);

      return result.isEmpty() ? null : result.get(result.size() - 1);
   }

   @Override
   public HtmlElement findElementById(String id)
   {
      return new JsoupHtmlElement(jsoupElement.getElementById(id));
   }

   @Override
   public String getText()
   {
      return jsoupElement.text();
   }

   @Override
   public String getAttribute(String attributeName)
   {
      return jsoupElement.attr(attributeName);
   }

   @Override
   public boolean hasClass(String className)
   {
      return jsoupElement.hasClass(className);
   }

   @Override
   public Map<String, String> getDataAttributes()
   {
      return jsoupElement.dataset();
   }

   @Override
   public String getValue()
   {
      return jsoupElement.val();
   }
}