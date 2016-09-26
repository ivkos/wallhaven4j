package com.ivkos.wallhaven4j.util.httpclient;

import java.util.Map;

public interface HttpClient
{
   HttpResponse execute(String method, String url, Map<String, String> headers, String body);

   HttpResponse get(String url, Map<String, String> headers);

   HttpResponse get(String url, Map<String, String> headers, Map<String, String> queryParams);

   HttpResponse get(String url);

   HttpResponse post(String url, Map<String, String> headers, String body);

   HttpResponse post(String url, Map<String, String> headers, Map<String, String> formParams);

   HttpResponse post(String url, String body);

   HttpResponse post(String url, Map<String, String> formParams);
}
