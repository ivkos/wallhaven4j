package com.ivkos.wallhaven4j.util.htmlparser;

import java.util.List;
import java.util.Map;

public interface HtmlElement
{
   List<HtmlElement> find(String cssQuery);

   HtmlElement findFirst(String cssQuery);

   HtmlElement findLast(String cssQuery);

   HtmlElement findElementById(String id);

   HtmlElement getNextElementSibling();

   String getText();

   String getAttribute(String attributeName);

   boolean hasClass(String className);

   Map<String, String> getDataAttributes();

   String getValue();

   String getTagName();
}
