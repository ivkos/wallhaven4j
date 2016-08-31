package com.ivkos.wallhaven4j.support;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivkos.wallhaven4j.support.httpclient.AbstractHttpClient;

@Singleton
public class WallhavenSession
{
   private final AbstractHttpClient httpClient;

   @Inject
   public WallhavenSession(AbstractHttpClient httpClient)
   {
      this.httpClient = httpClient;
   }

   public AbstractHttpClient getHttpClient()
   {
      return httpClient;
   }
}
