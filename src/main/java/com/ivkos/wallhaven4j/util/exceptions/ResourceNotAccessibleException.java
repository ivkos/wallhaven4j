package com.ivkos.wallhaven4j.util.exceptions;

/**
 * Thrown to indicate that the requested resource cannot be accessed due to privacy or purity restrictions.
 */
public class ResourceNotAccessibleException extends WallhavenException
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
