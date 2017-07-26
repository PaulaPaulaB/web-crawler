package com.wawelska.webcrawler;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientProvider {

   public HttpClient getHttpClient() {
        return new DefaultHttpClient();
    }

}
