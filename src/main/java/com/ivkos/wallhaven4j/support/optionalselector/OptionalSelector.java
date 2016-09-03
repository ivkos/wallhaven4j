package com.ivkos.wallhaven4j.support.optionalselector;

import com.ivkos.wallhaven4j.support.exceptions.ParseException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OptionalSelector
{
   private final Element context;
   private final String cssSelector;

   protected OptionalSelector(Element context, String cssSelector)
   {
      this.context = context;
      this.cssSelector = cssSelector;
   }

   public static OptionalSelector of(Element context, String cssSelector)
   {
      return new OptionalSelector(context, cssSelector);
   }

   public Element get() throws ParseException
   {
      Element first = select().first();

      if (first == null) {
         throw new ParseException("No such element: " + cssSelector);
      }

      return first;
   }

   public <X extends Throwable> Element orElseThrow(X throwable) throws X
   {
      Elements elements = select();

      if (elements.isEmpty()) {
         if (throwable.getCause() == null) {
            throwable.initCause(new ParseException("No such element: " + cssSelector));
         }

         throw throwable;
      }

      return elements.first();
   }

   private Elements select()
   {
      return context.select(cssSelector);
   }
}
