package com.ivkos.wallhaven4j.util.httpclient.apache;

import com.ivkos.wallhaven4j.util.exceptions.ConnectionException;
import com.ivkos.wallhaven4j.util.httpclient.HttpResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

class ApacheHttpResponse implements HttpResponse
{
   private final org.apache.http.HttpResponse apacheResponse;
   private final String body;

   ApacheHttpResponse(org.apache.http.HttpResponse apacheResponse)
   {
      this.apacheResponse = apacheResponse;
      this.body = getBodyEagerly();
   }

   @Override
   public int getStatusCode()
   {
      return apacheResponse.getStatusLine().getStatusCode();
   }

   @Override
   public String getBody()
   {
      return body;
   }

   private String getBodyEagerly()
   {
      HttpEntity theEntity = apacheResponse.getEntity();

      String body;
      try {
         body = EntityUtils.toString(theEntity);
         EntityUtils.consume(theEntity);
      } catch (IOException e) {
         throw new ConnectionException("Could not get response body", e);
      }

      return body;
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
