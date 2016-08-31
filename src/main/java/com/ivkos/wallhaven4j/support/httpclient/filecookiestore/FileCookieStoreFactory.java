package com.ivkos.wallhaven4j.support.httpclient.filecookiestore;

import java.io.File;

public interface FileCookieStoreFactory
{
   FileCookieStore create(File file);
   FileCookieStore create(String filename);
}
