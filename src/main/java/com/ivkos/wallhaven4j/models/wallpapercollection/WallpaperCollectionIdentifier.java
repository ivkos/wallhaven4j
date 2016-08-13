package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.ivkos.wallhaven4j.models.user.User;

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
}
