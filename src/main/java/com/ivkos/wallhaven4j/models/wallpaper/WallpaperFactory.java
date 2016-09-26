package com.ivkos.wallhaven4j.models.wallpaper;

import com.google.inject.assistedinject.Assisted;
import com.ivkos.wallhaven4j.models.ResourceFactory;
import com.ivkos.wallhaven4j.models.misc.Resolution;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;

public interface WallpaperFactory extends ResourceFactory<Wallpaper, Long>
{
   Wallpaper create(boolean preloadDom,
                    @Assisted("id") Long id,
                    Category category,
                    Purity purity,
                    Resolution resolution,
                    @Assisted("favoritesCount") long favoritesCount);
}
