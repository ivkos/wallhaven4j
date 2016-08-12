package com.ivkos.wallhaven4j.support.exceptions;

public class ConnectionException extends RuntimeException
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
