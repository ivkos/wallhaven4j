package com.ivkos.wallhaven4j.models;

public interface ResourceFactory<R extends AbstractResource<I>, I>
{
   R create(boolean preloadDom, I id);
}
