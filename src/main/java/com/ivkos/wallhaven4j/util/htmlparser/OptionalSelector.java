package com.ivkos.wallhaven4j.util.htmlparser;

import com.ivkos.wallhaven4j.util.exceptions.ParseException;

import java.util.List;
import java.util.function.Supplier;

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

   public <X extends Throwable> HtmlElement orElseThrow(X throwable) throws X
   {
      List<HtmlElement> elements = select();

      if (elements.isEmpty()) {
         if (throwable.getCause() == null) {
            throwable.initCause(new ParseException("No such element: " + cssSelector));
         }

         throw throwable;
      }

      return elements.get(0);
   }

   public <X extends Throwable> HtmlElement orElseThrowSupplied(Supplier<? extends X> throwable) throws X
   {
      List<HtmlElement> elements = select();

      if (elements.isEmpty()) {
         throw throwable.get();
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
