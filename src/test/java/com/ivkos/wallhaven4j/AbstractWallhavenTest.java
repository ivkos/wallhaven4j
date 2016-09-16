package com.ivkos.wallhaven4j;

import org.junit.BeforeClass;

import static org.junit.Assert.assertTrue;

public abstract class AbstractWallhavenTest
{
   private static Wallhaven wallhavenAnonymous;
   private static Wallhaven wallhavenLoggedIn;

   protected static String getWallhavenUsername()
   {
      return System.getenv("WALLHAVEN_USERNAME");
   }

   protected static String getWallhavenPassword()
   {
      return System.getenv("WALLHAVEN_PASSWORD");
   }

   @BeforeClass
   public static final void setUpBase()
   {
      assertTrue("Environment variable WALLHAVEN_USERNAME is not set",
            getWallhavenUsername() != null && !getWallhavenUsername().isEmpty());

      assertTrue("Environment variable WALLHAVEN_PASSWORD is not set",
            getWallhavenPassword() != null && !getWallhavenPassword().isEmpty());

      wallhavenAnonymous = new Wallhaven();
      wallhavenLoggedIn = new Wallhaven(
            getWallhavenUsername(),
            getWallhavenPassword()
      );
   }

   protected static Wallhaven getWallhaven(boolean loggedIn)
   {
      return loggedIn ? wallhavenLoggedIn : wallhavenAnonymous;
   }

   protected static Wallhaven getWallhaven()
   {
      return getWallhaven(false);
   }
}
