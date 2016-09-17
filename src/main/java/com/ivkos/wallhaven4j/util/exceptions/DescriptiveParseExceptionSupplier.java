package com.ivkos.wallhaven4j.util.exceptions;

import com.ivkos.wallhaven4j.models.AbstractResource;

import java.util.function.Supplier;

import static java.lang.String.format;

public class DescriptiveParseExceptionSupplier implements Supplier<ParseException>
{
   private final Supplier<ParseException> parseExceptionSupplier;

   protected DescriptiveParseExceptionSupplier(Supplier<ParseException> parseExceptionSupplier)
   {
      this.parseExceptionSupplier = parseExceptionSupplier;
   }

   public static <R extends AbstractResource> DescriptiveParseExceptionSupplier of(R thrower, String parsingWhat)
   {
      return new DescriptiveParseExceptionSupplier(() -> new ParseException(format("Could not parse %s of %s with id %s",
            parsingWhat, thrower.getClass().getSimpleName(), thrower.getId().toString()
      )));
   }

   public static <R extends AbstractResource> DescriptiveParseExceptionSupplier of(Class<R> throwingClass, String parsingWhat)
   {
      return new DescriptiveParseExceptionSupplier(() -> new ParseException(format("Could not parse %s of %s",
            parsingWhat, throwingClass.getSimpleName()
      )));
   }

   @Override
   public ParseException get()
   {
      return parseExceptionSupplier.get();
   }
}
