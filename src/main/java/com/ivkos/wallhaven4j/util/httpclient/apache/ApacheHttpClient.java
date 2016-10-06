package com.ivkos.wallhaven4j.util.httpclient.apache;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import com.google.inject.Inject;
import com.ivkos.wallhaven4j.util.exceptions.ConnectionException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;
import com.ivkos.wallhaven4j.util.httpclient.HttpClient;
import com.ivkos.wallhaven4j.util.httpclient.HttpResponse;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Collections2.transform;

public class ApacheHttpClient implements HttpClient
{
   private static final int CONNECTION_TIMEOUT_MS = 10 * 1000;

   private final org.apache.http.client.HttpClient apacheClient;

   @Inject
   ApacheHttpClient(org.apache.http.client.HttpClient apacheClient)
   {
      this.apacheClient = apacheClient;
   }

   private HttpResponse execute(final String method, String url, Map<String, String> headers, String body)
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
      base.setConfig(RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
            .setConnectTimeout(CONNECTION_TIMEOUT_MS)
            .setSocketTimeout(CONNECTION_TIMEOUT_MS)
            .build());

      if (headers != null && !headers.isEmpty()) {
         base.setHeaders(
               transform(headers.entrySet(), input -> new BasicHeader(input.getKey(), input.getValue()))
                     .toArray(new Header[0])
         );
      }

      if (body != null) {
         try {
            base.setEntity(new StringEntity(body));
         } catch (UnsupportedEncodingException e) {
            throw new ConnectionException(e);
         }
      }
      //endregion

      org.apache.http.HttpResponse response;
      try {
         response = apacheClient.execute(base);
      } catch (IOException e) {
         throw new ConnectionException(e);
      }

      //region Handle error codes
      int statusCode = response.getStatusLine().getStatusCode();

      if (statusCode >= 400) {
         EntityUtils.consumeQuietly(response.getEntity());
      }

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

      ApacheHttpResponse theResponse = new ApacheHttpResponse(response);

      base.releaseConnection();

      return theResponse;
   }

   @Override
   public HttpResponse get(String url, Map<String, String> headers)
   {
      return execute("GET", url, headers, null);
   }

   @Override
   public HttpResponse get(String url, Map<String, String> headers, Map<String, String> queryParams)
   {
      URIBuilder uriBuilder;
      try {
         uriBuilder = new URIBuilder(url);
      } catch (URISyntaxException e) {
         throw new ConnectionException(e);
      }

      for (Map.Entry<String, String> entry : queryParams.entrySet()) {
         uriBuilder.addParameter(entry.getKey(), entry.getValue());
      }

      return get(uriBuilder.toString(), headers);
   }

   public HttpResponse get(String url)
   {
      return get(url, Collections.emptyMap());
   }

   @Override
   public HttpResponse post(String url, Map<String, String> headers, String body)
   {
      return execute("POST", url, headers, body);
   }

   @Override
   public HttpResponse post(String url, Map<String, String> headers, Map<String, String> formParams)
   {
      Collection<NameValuePair> pairs = transform(formParams.entrySet(),
            input -> new BasicNameValuePair(input.getKey(), input.getValue()));

      Header contentType;
      String urlEncodedBody;
      try {
         UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
         contentType = urlEncodedFormEntity.getContentType();

         InputStream content = urlEncodedFormEntity.getContent();
         urlEncodedBody = CharStreams.toString(new InputStreamReader(content));
      } catch (IOException e) {
         throw new ConnectionException(e);
      }

      ImmutableMap<String, String> headers2 = ImmutableMap.<String, String>builder()
            .putAll(headers)
            .put(contentType.getName(), contentType.getValue())
            .build();

      return post(url, headers2, urlEncodedBody);
   }

   @Override
   public HttpResponse post(String url, Map<String, String> formParams)
   {
      return post(url, Collections.emptyMap(), formParams);
   }
}
