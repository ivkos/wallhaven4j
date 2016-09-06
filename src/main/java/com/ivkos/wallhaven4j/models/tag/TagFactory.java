package com.ivkos.wallhaven4j.models.tag;

import com.ivkos.wallhaven4j.models.ResourceFactory;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;

public interface TagFactory extends ResourceFactory<Tag, Long>
{
   Tag create(boolean preloadDom, Long id, String name, Purity purity);
}
