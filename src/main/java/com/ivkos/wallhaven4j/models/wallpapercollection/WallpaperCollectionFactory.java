package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.ivkos.wallhaven4j.models.support.ResourceFactory;
import com.ivkos.wallhaven4j.models.user.User;

public interface WallpaperCollectionFactory extends ResourceFactory<WallpaperCollection, Long>
{
   WallpaperCollection create(Long id, User user);
}
