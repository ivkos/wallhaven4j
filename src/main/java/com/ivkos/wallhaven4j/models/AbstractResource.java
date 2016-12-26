package com.ivkos.wallhaven4j.models;

import com.ivkos.wallhaven4j.util.ResourceFieldGetter;
import com.ivkos.wallhaven4j.util.WallhavenSession;
import com.ivkos.wallhaven4j.util.exceptions.WallhavenException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractResource<T>
{
   protected final T id;

   private final WallhavenSession session;
   private HtmlElement dom;

   protected AbstractResource(WallhavenSession session, boolean preloadDom, T id)
   {
      this.session = session;
      this.id = checkNotNull(id, "id must not be null");

      if (preloadDom) {
         getDom();
      }
   }

   protected final HtmlElement getDom()
   {
      if (this.dom != null) {
         return this.dom;
      }

      String html = getSession().getHttpClient().get(getUrl()).getBody();
      this.dom = getSession().getHtmlParser().parse(html, getUrl());

      return this.dom;
   }

   protected final void populateFields()
   {
      for (Method method : getClass().getMethods()) {
         boolean isFieldGetter = method.isAnnotationPresent(ResourceFieldGetter.class);

         if (!isFieldGetter) continue;

         try {
            method.invoke(this);
         } catch (IllegalAccessException e) {
            throw new WallhavenException("Could not populate fields of resource (" + this.toString(true) + ")", e);
         } catch (InvocationTargetException e) {
            throw new WallhavenException("Could not populate fields of resource (" + this.toString(true) + ")",
                  e.getCause());
         }
      }
   }

   /**
    * Returns the ID of this resource
    *
    * @return the ID of this resource
    */
   public final T getId()
   {
      return id;
   }

   protected final WallhavenSession getSession()
   {
      return session;
   }

   /**
    * Returns a browseable URL of this resource
    *
    * @return the URL of this resource
    */
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

   /**
    * Returns a string representation of the ID of this resource
    *
    * @return a string representation of the ID of this resource
    */
   @Override
   public String toString()
   {
      return this.toString(false);
   }

   /**
    * Returns a string description of this resource including its resource type and ID, e.g. "Wallpaper 42"
    *
    * @param withResourceType whether to include the resource type in the description
    * @return string description of this resource
    */
   public String toString(boolean withResourceType)
   {
      String id = this.getId().toString();

      return withResourceType
            ? String.format("%s %s", this.getClass().getSimpleName(), id)
            : id;
   }
   //endregion
}
