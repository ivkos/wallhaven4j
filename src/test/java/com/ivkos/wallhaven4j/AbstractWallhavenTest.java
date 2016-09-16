package com.ivkos.wallhaven4j;

import org.junit.BeforeClass;

public abstract class AbstractWallhavenTest
{
   private static Wallhaven wallhavenAnonymous;
   private static Wallhaven wallhavenLoggedIn;

   @BeforeClass
   public static final void setUpBase()
   {
      wallhavenAnonymous = new Wallhaven();
      wallhavenLoggedIn = new Wallhaven(
            System.getenv("WALLHAVEN_USERNAME"),
            System.getenv("WALLHAVEN_PASSWORD")
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
