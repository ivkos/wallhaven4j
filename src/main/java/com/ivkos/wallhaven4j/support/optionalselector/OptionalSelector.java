package com.ivkos.wallhaven4j.support.optionalselector;

import com.ivkos.wallhaven4j.support.exceptions.ParseException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OptionalSelector<T>
{
   private final T context;
   private final String cssSelector;

   private OptionalSelector(T context, String cssSelector)
   {
      this.context = context;
      this.cssSelector = cssSelector;
   }

   public static <T extends Element> OptionalSelector<T> of(T context, String cssSelector)
   {
      return new OptionalSelector<>(context, cssSelector);
   }

   public static <T extends Elements> OptionalSelector<T> of(T context, String cssSelector)
   {
      return new OptionalSelector<>(context, cssSelector);
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
      if (context instanceof Element) {
         return ((Element) context).select(cssSelector);
      } else if (context instanceof Elements) {
         return ((Elements) context).select(cssSelector);
      } else {
         throw new ClassCastException("Illegal context");
      }
   }
}
