package com.ivkos.wallhaven4j.util.exceptions;

public class ParseException extends WallhavenException
{
   public ParseException()
   {
   }

   public ParseException(String message)
   {
      super(message);
   }

   public ParseException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public ParseException(Throwable cause)
   {
      super(cause);
   }

   public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
