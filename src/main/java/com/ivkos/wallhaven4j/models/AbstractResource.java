package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public abstract class AbstractResource<T>
{
   protected final T id;

   private final WallhavenSession session;
   private HtmlElement dom;

   protected AbstractResource(WallhavenSession session, boolean preloadDom, T id)
   {
      this.session = session;
      this.id = id;

      if (preloadDom) {
         getDom();
      }
   }

   protected HtmlElement getDom(boolean enableCache)
   {
      if (this.dom != null && enableCache) {
         return this.dom;
      }

      String html = getSession().getHttpClient().get(getUrl()).getBody();
      this.dom = getSession().getHtmlParser().parse(html, getUrl());

      return this.dom;
   }

   protected HtmlElement getDom()
   {
      return getDom(true);
   }

   protected void populateFields()
   {
      for (Method method : getClass().getMethods()) {
         boolean isFieldGetter = method.isAnnotationPresent(ResourceFieldGetter.class);

         if (!isFieldGetter) continue;

         try {
            method.invoke(this);
         } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("A failure of epic proportions has occurred", e);
         }
      }
   }

   public final T getId()
   {
      return id;
   }

   protected WallhavenSession getSession()
   {
      return session;
   }

   public abstract String getUrl();

   //region equals, hashCode, toString
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
   //endregion
}
