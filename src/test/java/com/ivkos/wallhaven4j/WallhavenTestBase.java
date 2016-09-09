package com.ivkos.wallhaven4j;

import org.junit.BeforeClass;

public abstract class WallhavenTestBase
{
   private static Wallhaven wallhavenAnonymous;
   private static Wallhaven wallhavenLoggedIn;

   protected WallhavenTestBase()
   {
   }

   @BeforeClass
   public static void setUp()
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
