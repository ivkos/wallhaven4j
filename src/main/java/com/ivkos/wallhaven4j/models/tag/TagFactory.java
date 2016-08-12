package com.ivkos.wallhaven4j.models.tag;

import com.ivkos.wallhaven4j.models.support.ResourceFactory;

public interface TagFactory extends ResourceFactory<Tag, Long>
{
   Tag create(Long id, String name);
}
