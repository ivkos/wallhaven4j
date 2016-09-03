package com.ivkos.wallhaven4j.support.httpclient;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import com.google.inject.Inject;
import com.ivkos.wallhaven4j.support.exceptions.ConnectionException;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.support.exceptions.ResourceNotFoundException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Collections2.transform;

public class ApacheHttpClient implements HttpClient
{
   private final CloseableHttpClient client;

   @Inject
   public ApacheHttpClient(CloseableHttpClient client)
   {
      this.client = client;
   }

   @Override
   public String execute(final String method, String url, Map<String, String> headers, String body)
   {
      //region Build request
      HttpEntityEnclosingRequestBase base = new HttpEntityEnclosingRequestBase()
      {
         @Override
         public String getMethod()
         {
            return method;
         }
      };

      base.setURI(URI.create(url));

      if (headers != null && !headers.isEmpty()) {
         base.setHeaders(transform(headers.entrySet(), new Function<Map.Entry<String, String>, Header>()
         {
            @Override
            public Header apply(Map.Entry<String, String> input)
            {
               return new BasicHeader(input.getKey(), input.getValue());
            }
         }).toArray(new Header[0]));
      }

      if (body != null) {
         try {
            base.setEntity(new StringEntity(body));
         } catch (UnsupportedEncodingException e) {
            throw new ConnectionException(e);
         }
      }
      //endregion

      HttpResponse response;
      try {
         response = client.execute(base);
      } catch (IOException e) {
         throw new ConnectionException(e);
      }

      //region Handle error codes
      int statusCode = response.getStatusLine().getStatusCode();

      if (statusCode == 404) {
         throw new ResourceNotFoundException("Resource not found");
      }

      if (statusCode == 403) {
         throw new ResourceNotAccessibleException("Access to resource is forbidden");
      }

      if (statusCode >= 400) {
         throw new ConnectionException("Could not get resource due to HTTP error " + statusCode);
      }
      //endregion

      String responseBody;
      try {
         responseBody = EntityUtils.toString(response.getEntity());
      } catch (IOException e) {
         throw new ConnectionException("Could not get response entity", e);
      }

      return responseBody;
   }


   @Override
   public String get(String url, Map<String, String> headers)
   {
      return execute("GET", url, headers, null);
   }

   public String get(String url)
   {
      return get(url, Collections.<String, String>emptyMap());
   }

   @Override
   public String post(String url, Map<String, String> headers, String body)
   {
      return execute("POST", url, headers, body);
   }

   @Override
   public String post(String url, Map<String, String> headers, Map<String, String> formParams)
   {
      Collection<NameValuePair> pairs = Collections2.transform(formParams.entrySet(),
            new Function<Map.Entry<String, String>, NameValuePair>()
            {
               @Override
               public NameValuePair apply(Map.Entry<String, String> input)
               {
                  return new BasicNameValuePair(input.getKey(), input.getValue());
               }
            });

      Header contentType;
      String urlEncodedBody;
      try {
         UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
         contentType = urlEncodedFormEntity.getContentType();

         InputStream content = urlEncodedFormEntity.getContent();
         urlEncodedBody = CharStreams.toString(new InputStreamReader(content));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      ImmutableMap<String, String> headers2 = ImmutableMap.<String, String>builder()
            .putAll(headers)
            .put(contentType.getName(), contentType.getValue())
            .build();

      return post(url, headers2, urlEncodedBody);
   }

   public String post(String url, String body)
   {
      return post(url, Collections.<String, String>emptyMap(), body);
   }

   @Override
   public String post(String url, Map<String, String> formParams)
   {
      return post(url, Collections.<String, String>emptyMap(), formParams);
   }
}
