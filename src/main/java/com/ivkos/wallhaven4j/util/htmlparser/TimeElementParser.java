package com.ivkos.wallhaven4j.util.htmlparser;

import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import org.joda.time.DateTime;

public abstract class TimeElementParser
{
   public static DateTime parse(HtmlElement htmlElement)
   {
      if (!htmlElement.getTagName().equals("time")) {
         throw new IllegalArgumentException("htmlElement must be a 'time' element");
      }

      String datetime = htmlElement.getAttribute("datetime");

      if (datetime.isEmpty()) throw new ParseException("datetime is empty");

      DateTime result;
      try {
         result = DateTime.parse(datetime);
      } catch (IllegalArgumentException e) {
         throw new ParseException("Could not parse time element", e);
      }

      return result;
   }
}
