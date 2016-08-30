package com.ivkos.wallhaven4j.support.exceptions;

public class ResourceNotAccessibleException extends RuntimeException
{
   public ResourceNotAccessibleException()
   {
   }

   public ResourceNotAccessibleException(String message)
   {
      super(message);
   }

   public ResourceNotAccessibleException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public ResourceNotAccessibleException(Throwable cause)
   {
      super(cause);
   }

   public ResourceNotAccessibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
