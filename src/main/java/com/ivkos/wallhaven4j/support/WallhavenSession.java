package com.ivkos.wallhaven4j.support;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.ivkos.wallhaven4j.support.exceptions.LoginException;
import com.ivkos.wallhaven4j.support.exceptions.ParseException;
import com.ivkos.wallhaven4j.support.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.support.htmlparser.HtmlParser;
import com.ivkos.wallhaven4j.support.httpclient.HttpClient;
import com.ivkos.wallhaven4j.support.htmlparser.OptionalSelector;

import static com.ivkos.wallhaven4j.support.UrlPrefixes.URL_BASE;
import static com.ivkos.wallhaven4j.support.UrlPrefixes.URL_LOGIN;

public class WallhavenSession
{
   private final HttpClient httpClient;
   private final HtmlParser htmlParser;

   private String username;

   @Inject
   public WallhavenSession(HttpClient httpClient, HtmlParser htmlParser)
   {
      this.httpClient = httpClient;
      this.htmlParser = htmlParser;
   }

   public HttpClient getHttpClient()
   {
      return httpClient;
   }

   public HtmlParser getHtmlParser()
   {
      return htmlParser;
   }

   public void login(String username, String password)
   {
      Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "username may not be empty");
      Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "password may not be empty");

      String token = getLoginToken();

      String html = httpClient.post(URL_LOGIN, ImmutableMap.of(
            "_token", token,
            "username", username,
            "password", password
      ));

      ParseException parseException = new ParseException("Unexpected login redirect behavior");

      HtmlElement dom = htmlParser.parse(html, URL_LOGIN);
      HtmlElement metaRefresh = OptionalSelector.of(dom.findFirst("head"), "meta[http-equiv=refresh]")
            .orElseThrow(parseException);

      String redirectUrl;
      try {
         redirectUrl = metaRefresh.getAttribute("content").split("url=")[1];
      } catch (IndexOutOfBoundsException e) {
         parseException.initCause(e);
         throw parseException;
      }

      if (Strings.isNullOrEmpty(redirectUrl)) {
         throw parseException;
      }

      if (redirectUrl.equals(URL_LOGIN)) {
         throw new LoginException("Username or password incorrect");
      }

      if (!redirectUrl.equals(URL_BASE)) {
         throw parseException;
      }
   }

   private String getLoginToken()
   {
      String html = getHttpClient().get(URL_BASE);
      HtmlElement dom = htmlParser.parse(html, URL_BASE);

      ParseException parseException = new ParseException("Could not parse login token");

      String token = OptionalSelector
            .of(dom, "input[name=\"_token\"]")
            .orElseThrow(parseException)
            .getValue();

      if (token.isEmpty()) {
         throw parseException;
      }

      return token;
   }
}
