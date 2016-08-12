package com.ivkos.wallhaven4j.models.support;

import com.google.inject.Inject;
import com.ivkos.wallhaven4j.support.WallhavenSession;

import java.util.Objects;

public abstract class AbstractResource<T>
{
   public abstract T getId();

   public abstract String getUrl();

   @Inject
   private final WallhavenSession session;

   protected AbstractResource(WallhavenSession session)
   {
      this.session = session;
   }

   protected WallhavenSession getSession()
   {
      return session;
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
