package com.ivkos.wallhaven4j.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivkos.wallhaven4j.models.ResourceFactoryFactory;
import com.ivkos.wallhaven4j.models.user.User;
import com.ivkos.wallhaven4j.util.exceptions.LoginException;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlElement;
import com.ivkos.wallhaven4j.util.htmlparser.HtmlParser;
import com.ivkos.wallhaven4j.util.htmlparser.OptionalSelector;
import com.ivkos.wallhaven4j.util.httpclient.HttpClient;

import static com.ivkos.wallhaven4j.util.UrlPrefixes.URL_BASE;
import static com.ivkos.wallhaven4j.util.UrlPrefixes.URL_LOGIN;

@Singleton
public class WallhavenSession
{
   private final HttpClient httpClient;
   private final HtmlParser htmlParser;
   private final ResourceFactoryFactory rff;

   private User currentUser;

   @Inject
   WallhavenSession(HttpClient httpClient, HtmlParser htmlParser, ResourceFactoryFactory rff)
   {
      this.httpClient = httpClient;
      this.htmlParser = htmlParser;
      this.rff = rff;
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

      String token;
      {
         String indexHtml = getHttpClient().get(URL_BASE).getBody();
         HtmlElement indexDom = getHtmlParser().parse(indexHtml, URL_BASE);

         if (username.equals(getLoggedInUsername(indexDom))) {
            this.currentUser = rff.getFactoryFor(User.class).create(false, username);
            return;
         }

         token = getLoginToken(indexDom);
      }

      String html = httpClient.post(URL_LOGIN, ImmutableMap.of(
            "_token", token,
            "username", username,
            "password", password
      )).getBody();

      Supplier<ParseException> parseExceptionSupplier = () -> new ParseException("Unexpected login redirect behavior");

      HtmlElement dom = htmlParser.parse(html, URL_LOGIN);
      HtmlElement metaRefresh = OptionalSelector.of(dom.findFirst("head"), "meta[http-equiv=refresh]")
            .orElseThrow(parseExceptionSupplier);

      String redirectUrl;
      try {
         redirectUrl = metaRefresh.getAttribute("content").split("url=")[1];
      } catch (IndexOutOfBoundsException e) {
         ParseException parseException = parseExceptionSupplier.get();
         parseException.initCause(e);
         throw parseException;
      }

      if (Strings.isNullOrEmpty(redirectUrl)) {
         throw parseExceptionSupplier.get();
      }

      if (redirectUrl.equals(URL_LOGIN)) {
         throw new LoginException("Username or password incorrect");
      }

      if (!redirectUrl.equals(URL_BASE)) {
         throw parseExceptionSupplier.get();
      }

      this.currentUser = rff.getFactoryFor(User.class).create(false, username);
   }

   public User getCurrentUser()
   {
      return currentUser;
   }

   private String getLoginToken(HtmlElement dom)
   {
      Supplier<ParseException> parseExceptionSupplier = () -> new ParseException("Could not parse login token");

      String token = OptionalSelector
            .of(dom, "input[name=\"_token\"]")
            .orElseThrow(parseExceptionSupplier)
            .getValue();

      if (token.isEmpty()) {
         throw parseExceptionSupplier.get();
      }

      return token;
   }

   private String getLoggedInUsername(HtmlElement dom)
   {
      OptionalSelector os = OptionalSelector.of(dom, "#userpanel > a > span.username");

      if (!os.isPresent()) return null;

      return os.get().getText();
   }
}
