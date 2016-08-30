package com.ivkos.wallhaven4j.models.support;

public interface ResourceFactory<R extends AbstractResource, I>
{
   R create(boolean preloadDom, I id);
}