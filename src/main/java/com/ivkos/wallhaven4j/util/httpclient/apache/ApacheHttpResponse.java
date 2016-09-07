package com.ivkos.wallhaven4j.util.httpclient.apache;

import com.ivkos.wallhaven4j.util.exceptions.ConnectionException;
import com.ivkos.wallhaven4j.util.httpclient.HttpResponse;
import org.apache.http.Header;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

class ApacheHttpResponse implements HttpResponse
{
   private final org.apache.http.HttpResponse apacheResponse;

   ApacheHttpResponse(org.apache.http.HttpResponse apacheResponse)
   {
      this.apacheResponse = apacheResponse;
   }

   @Override
   public int getStatusCode()
   {
      return apacheResponse.getStatusLine().getStatusCode();
   }

   @Override
   public String getBody()
   {
      try {
         return EntityUtils.toString(apacheResponse.getEntity());
      } catch (IOException e) {
         throw new ConnectionException("Could not get response entity", e);
      }
   }

   @Override
   public Map<String, String> getHeaders()
   {
      Map<String, String> headersMap = new HashMap<>();

      for (Header header : apacheResponse.getAllHeaders()) {
         headersMap.put(header.getName(), header.getValue());
      }

      return unmodifiableMap(headersMap);
   }
}
