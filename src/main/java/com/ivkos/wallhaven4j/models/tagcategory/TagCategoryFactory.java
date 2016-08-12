package com.ivkos.wallhaven4j.models.tagcategory;

import com.ivkos.wallhaven4j.models.support.ResourceFactory;

public interface TagCategoryFactory extends ResourceFactory<TagCategory, Long>
{
   TagCategory create(Long id, String name);

   TagCategory create(Long id, String name, TagCategory parentCategory);
}
