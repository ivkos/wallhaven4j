package com.ivkos.wallhaven4j.util.exceptions;

/**
 * Thrown to indicate a problem with the HTTP connection to Wallhaven.
 */
public class ConnectionException extends WallhavenException
{
   public ConnectionException()
   {
   }

   public ConnectionException(String message)
   {
      super(message);
   }

   public ConnectionException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public ConnectionException(Throwable cause)
   {
      super(cause);
   }

   public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
