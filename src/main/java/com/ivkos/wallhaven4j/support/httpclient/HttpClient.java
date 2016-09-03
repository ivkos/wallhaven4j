package com.ivkos.wallhaven4j.support.httpclient;

import java.util.Map;

public interface HttpClient
{
   String execute(String method, String url, Map<String, String> headers, String body);

   String get(String url, Map<String, String> headers);

   String get(String url);

   String post(String url, Map<String, String> headers, String body);

   String post(String url, Map<String, String> headers, Map<String, String> formParams);

   String post(String url, String body);

   String post(String url, Map<String, String> formParams);
}
