package com.ivkos.wallhaven4j.models;

public interface ResourceFactory<R extends AbstractResource, I>
{
   R create(boolean preloadDom, I id);
}
