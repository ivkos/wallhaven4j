package com.ivkos.wallhaven4j.support.httpclient;

import java.util.Map;

public interface AbstractHttpClient
{
   String execute(String method, String url, Map<String, String> headers, String body);

   String get(String url, Map<String, String> headers);

   String post(String url, Map<String, String> headers, String body);
}
