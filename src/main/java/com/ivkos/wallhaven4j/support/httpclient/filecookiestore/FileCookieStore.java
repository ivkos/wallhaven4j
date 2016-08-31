package com.ivkos.wallhaven4j.support.httpclient.filecookiestore;

import com.google.common.io.Files;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.ivkos.wallhaven4j.support.httpclient.jsonserializer.JsonSerializer;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

public class FileCookieStore implements CookieStore
{
   private final JsonSerializer jsonSerializer;
   private final File file;
   private final CookieStore store = new BasicCookieStore();

   @AssistedInject
   FileCookieStore(JsonSerializer jsonSerializer, @Assisted File file)
   {
      this.jsonSerializer = jsonSerializer;
      this.file = file;

      if (!file.exists()) {
         try {
            Files.createParentDirs(file);
            Files.touch(file);

         } catch (IOException e) {
            throw new RuntimeException("Could not create file", e);
         }
      }

      readCookiesFromFile();
   }

   @AssistedInject
   FileCookieStore(JsonSerializer jsonSerializer, @Assisted String filename)
   {
      this(jsonSerializer, new File(filename));
   }

   private void readCookiesFromFile()
   {
      String json;
      try {
         json = Files.toString(file, Charset.defaultCharset());
      } catch (IOException e) {
         throw new RuntimeException("Could not load cookies from file", e);
      }

      if (json.isEmpty()) return;

      BasicCookieStore cookiesInFile = jsonSerializer.fromJson(json, BasicCookieStore.class);

      for (Cookie cookie : cookiesInFile.getCookies()) {
         store.addCookie(cookie);
      }
   }

   private void writeCookiesToFile() {
      String json = jsonSerializer.toJson(store);

      try {
         Files.write(json.getBytes(), file);
      } catch (IOException e) {
         throw new RuntimeException("Could not write cookies to file", e);
      }
   }

   @Override
   public void addCookie(Cookie cookie)
   {
      store.addCookie(cookie);
      writeCookiesToFile();
   }

   @Override
   public List<Cookie> getCookies()
   {
      return store.getCookies();
   }

   @Override
   public boolean clearExpired(Date date)
   {
      boolean result = store.clearExpired(date);
      writeCookiesToFile();
      return result;
   }

   @Override
   public void clear()
   {
      store.clear();
      writeCookiesToFile();
   }
}
