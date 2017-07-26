package com.wawelska.webcrawler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.wawelska.webcrawler.Main.DEBUG;

public class WebClientImpl implements WebClient {

    private final String USER_AGENT = "Mozilla/5.0";

    private final HttpClientProvider httpClientProvider;

    public WebClientImpl(HttpClientProvider provider) {
        this.httpClientProvider = provider;
    }

    public String getContent(String url) throws Exception {

        HttpClient client = httpClientProvider.getHttpClient();
        HttpGet request = new HttpGet(url);

        try {
            // add request header
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = client.execute(request);

            if (DEBUG) {
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " +
                        response.getStatusLine().getStatusCode());
            }

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception("No content!");
            }

            StringBuilder result = new StringBuilder();

            try (BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))
            ) {
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }
            return result.toString();
        } finally {
            request.releaseConnection();
        }
    }
}
