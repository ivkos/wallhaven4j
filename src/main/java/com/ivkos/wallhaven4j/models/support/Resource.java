package com.ivkos.wallhaven4j.models.support;

import java.util.Objects;

public abstract class Resource<T>
{
   public abstract T getId();

   public abstract String getUrl();

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof Resource)) return false;
      if (this.getClass() != o.getClass()) return false;

      Resource<?> other = (Resource<?>) o;

      return this.getId().equals(other.getId());
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(this.getClass(), getId());
   }

   @Override
   public String toString()
   {
      return this.getClass().getSimpleName() + " " + getId();
   }
}
