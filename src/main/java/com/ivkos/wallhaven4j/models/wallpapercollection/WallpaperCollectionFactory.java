package com.ivkos.wallhaven4j.models.wallpapercollection;

import com.ivkos.wallhaven4j.models.ResourceFactory;

public interface WallpaperCollectionFactory extends ResourceFactory<WallpaperCollection, WallpaperCollectionIdentifier>
{
   WallpaperCollection create(boolean preloadDom, WallpaperCollectionIdentifier id, String name);
}
