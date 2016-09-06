package com.ivkos.wallhaven4j.models.tagcategory;

import com.ivkos.wallhaven4j.models.ResourceFactory;

public interface TagCategoryFactory extends ResourceFactory<TagCategory, Long>
{
   TagCategory create(boolean preloadDom, Long id, String name);

   TagCategory create(boolean preloadDom, Long id, String name, TagCategory parentCategory);
}
