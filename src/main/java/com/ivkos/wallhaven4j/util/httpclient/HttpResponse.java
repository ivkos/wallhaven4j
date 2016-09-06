package com.ivkos.wallhaven4j.util.httpclient;

import java.util.Map;

public interface HttpResponse
{
   int getStatusCode();

   String getBody();

   Map<String, String> getHeaders();
}
