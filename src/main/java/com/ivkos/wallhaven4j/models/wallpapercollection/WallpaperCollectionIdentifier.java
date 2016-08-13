package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.ivkos.wallhaven4j.models.user.User;

import java.util.Objects;

public class WallpaperCollectionIdentifier
{
   private final long longId;
   private final User user;

   public WallpaperCollectionIdentifier(long longId, User user)
   {
      this.longId = longId;
      this.user = user;
   }

   public long getLongId()
   {
      return longId;
   }

   public User getUser()
   {
      return user;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof WallpaperCollectionIdentifier)) return false;
      WallpaperCollectionIdentifier that = (WallpaperCollectionIdentifier) o;
      return getLongId() == that.getLongId() &&
            Objects.equals(getUser(), that.getUser());
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(getLongId(), getUser());
   }
}
