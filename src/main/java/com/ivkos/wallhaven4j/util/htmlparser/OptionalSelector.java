package com.ivkos.wallhaven4j.util.htmlparser;

import com.google.common.base.Supplier;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class OptionalSelector
{
   private final HtmlElement context;
   private final String cssSelector;

   protected OptionalSelector(HtmlElement context, String cssSelector)
   {
      checkNotNull(context, "context must not be null");
      checkNotNull(context, "cssSelector must not be null");

      this.context = context;
      this.cssSelector = cssSelector;
   }

   public static OptionalSelector of(HtmlElement context, String cssSelector)
   {
      return new OptionalSelector(context, cssSelector);
   }

   public HtmlElement get() throws ParseException
   {
      List<HtmlElement> elements = select();

      if (elements.isEmpty()) {
         throw new ParseException("No such element: " + cssSelector);
      }

      return elements.get(0);
   }

   public <X extends Throwable> HtmlElement orElseThrow(Supplier<? extends X> throwable) throws X
   {
      List<HtmlElement> elements = select();

      if (elements.isEmpty()) {
         X x = throwable.get();

         if (x.getCause() == null) {
            x.initCause(new ParseException("No such element: " + cssSelector));
         }

         throw x;
      }

      return elements.get(0);
   }

   public boolean isPresent()
   {
      return !select().isEmpty();
   }

   private List<HtmlElement> select()
   {
      return context.find(cssSelector);
   }
}
