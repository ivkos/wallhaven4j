package com.ivkos.wallhaven4j.util.exceptions;

/**
 * Thrown to indicate that the requested resource cannot be found.
 */
public class ResourceNotFoundException extends WallhavenException
{
   public ResourceNotFoundException()
   {
   }

   public ResourceNotFoundException(String message)
   {
      super(message);
   }

   public ResourceNotFoundException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public ResourceNotFoundException(Throwable cause)
   {
      super(cause);
   }

   public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
