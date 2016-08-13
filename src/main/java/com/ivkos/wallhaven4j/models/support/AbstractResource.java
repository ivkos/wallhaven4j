package com.ivkos.wallhaven4j.models.support;

import com.ivkos.wallhaven4j.support.WallhavenSession;
import com.ivkos.wallhaven4j.support.exceptions.ConnectionException;
import com.ivkos.wallhaven4j.support.exceptions.ParseException;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotFoundException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public abstract class AbstractResource<T>
{
   protected final T id;

   private final WallhavenSession session;
   private Document dom;

   protected AbstractResource(WallhavenSession session, boolean preloadDom, T id)
   {
      this.session = session;
      this.id = id;

      if (preloadDom) {
         getDom();
      }
   }

   public final T getId()
   {
      return id;
   }

   public abstract String getUrl();

   protected WallhavenSession getSession()
   {
      return session;
   }

   protected Document getDom()
   {
      return getDom(true);
   }

   protected Document getDom(boolean enableCache)
   {
      if (this.dom != null && enableCache) {
         return this.dom;
      }

      HttpGet get = new HttpGet(getUrl());

      HttpResponse response;
      try {
         response = getSession().getHttpClient().execute(get);
      } catch (IOException e) {
         throw new ConnectionException("Could not connect", e);
      }

      int statusCode = response.getStatusLine().getStatusCode();

      if (statusCode == 404) {
         throw new ResourceNotFoundException(this.toString() + " not found.");
      }

      if (statusCode == 403) {
         throw new ResourceNotAccessibleException("Access to " + this.toString() + " is forbidden.");
      }

      if (statusCode >= 400) {
         throw new ConnectionException("Could not get " + this.toString() + " due to HTTP error " + statusCode);
      }

      InputStream contentStream;
      try {
         contentStream = response.getEntity().getContent();
      } catch (IOException e) {
         throw new ParseException("Could not get content stream");
      }

      try {
         this.dom = Jsoup.parse(contentStream, "utf-8", getUrl());
      } catch (IOException e) {
         throw new ParseException(e);
      }

      return this.dom;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AbstractResource)) return false;
      if (this.getClass() != o.getClass()) return false;

      AbstractResource<?> other = (AbstractResource<?>) o;

      return Objects.equals(this.getId(), other.getId());
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(this.getClass(), this.getId());
   }

   @Override
   public String toString()
   {
      return this.getClass().getSimpleName() + " " + this.getId();
   }
}
