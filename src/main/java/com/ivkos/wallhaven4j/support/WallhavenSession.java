package com.ivkos.wallhaven4j.support;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.ivkos.wallhaven4j.support.exceptions.LoginException;
import com.ivkos.wallhaven4j.support.exceptions.ParseException;
import com.ivkos.wallhaven4j.support.httpclient.AbstractHttpClient;
import com.ivkos.wallhaven4j.support.optionalselector.OptionalSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static com.ivkos.wallhaven4j.support.UrlPrefixes.URL_BASE;
import static com.ivkos.wallhaven4j.support.UrlPrefixes.URL_LOGIN;

public class WallhavenSession
{
   private final AbstractHttpClient httpClient;

   private String username;

   @Inject
   public WallhavenSession(AbstractHttpClient httpClient)
   {
      this.httpClient = httpClient;
   }

   public AbstractHttpClient getHttpClient()
   {
      return httpClient;
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

      Document dom = Jsoup.parse(html);
      Element metaRefresh = OptionalSelector.of(dom.head(), "meta[http-equiv=refresh]")
            .orElseThrow(parseException);

      String redirectUrl;
      try {
         redirectUrl = metaRefresh.attr("content").split("url=")[1];
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
      String html = getHttpClient().get(UrlPrefixes.URL_BASE);
      Document dom = Jsoup.parse(html);

      ParseException parseException = new ParseException("Could not parse login token");

      String token = OptionalSelector
            .of(dom, "input[name=\"_token\"]")
            .orElseThrow(parseException)
            .val();

      if (token.isEmpty()) {
         throw parseException;
      }

      return token;
   }
}
