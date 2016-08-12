package com.ivkos.wallhaven4j.support;

import com.google.inject.Singleton;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;

@Singleton
public class WallhavenSession implements AutoCloseable
{
   private final HttpClient httpClient;
   private final String username;

   public WallhavenSession()
   {
      this.httpClient = HttpClients.createDefault();
      this.username = null;
   }

   public WallhavenSession(String username, String password)
   {
      this.httpClient = HttpClients.custom()
            .setDefaultCookieStore(new BasicCookieStore())
            .build();

      this.username = username;
   }

   public HttpClient getHttpClient()
   {
      return httpClient;
   }

   @Override
   public void close() throws Exception
   {
      ((Closeable) this.httpClient).close();
   }

   public String getUsername()
   {
      return username;
   }
}
