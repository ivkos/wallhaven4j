package com.ivkos.wallhaven4j.util.exceptions;

/**
 * Thrown to indicate that logging in to Wallhaven was unsuccessful due to incorrect credentials.
 */
public class LoginException extends WallhavenException
{
   public LoginException()
   {
   }

   public LoginException(String message)
   {
      super(message);
   }

   public LoginException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public LoginException(Throwable cause)
   {
      super(cause);
   }

   public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
