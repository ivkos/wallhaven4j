package com.ivkos.wallhaven4j.util.exceptions;

/**
 * Thrown to indicate a problem with the library.
 */
public class WallhavenException extends RuntimeException
{
   public WallhavenException()
   {
   }

   public WallhavenException(String message)
   {
      super(message);
   }

   public WallhavenException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public WallhavenException(Throwable cause)
   {
      super(cause);
   }

   public WallhavenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
