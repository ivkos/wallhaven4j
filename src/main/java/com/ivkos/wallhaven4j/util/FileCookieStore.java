package com.ivkos.wallhaven4j.util;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.ivkos.wallhaven4j.util.exceptions.WallhavenException;
import com.ivkos.wallhaven4j.util.jsonserializer.JsonSerializer;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Set;

class FileCookieStore implements CookieStore
{
   private final JsonSerializer jsonSerializer;
   private final File cookiesFile;
   private final CookieStore store = new BasicCookieStore();

   FileCookieStore(JsonSerializer jsonSerializer, File cookiesFile)
   {
      this.jsonSerializer = jsonSerializer;
      this.cookiesFile = cookiesFile;

      if (!cookiesFile.exists()) {
         try {
            Files.createParentDirs(cookiesFile);
            Files.touch(cookiesFile);
         } catch (IOException e) {
            throw new WallhavenException("Could not create cookie file", e);
         }
      }

      readCookiesFromFile();
   }

   private void readCookiesFromFile()
   {
      String json;
      try {
         json = Files.toString(cookiesFile, Charset.defaultCharset());
      } catch (IOException e) {
         throw new WallhavenException("Could not load cookies from file", e);
      }

      if (json.isEmpty()) return;

      TypeToken<Set<BasicClientCookie>> typeToken = new TypeToken<Set<BasicClientCookie>>()
      {
      };

      Set<Cookie> cookiesInFile = jsonSerializer.fromJson(json, typeToken.getType());

      for (Cookie cookie : cookiesInFile) {
         store.addCookie(cookie);
      }
   }

   private void writeCookiesToFile()
   {
      String json = jsonSerializer.toJson(store.getCookies());

      try {
         Files.write(json.getBytes(), cookiesFile);
      } catch (IOException e) {
         throw new WallhavenException("Could not write cookies to file", e);
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
