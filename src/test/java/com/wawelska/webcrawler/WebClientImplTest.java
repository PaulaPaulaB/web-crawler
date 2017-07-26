package com.wawelska.webcrawler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WebClientImplTest {

    private WebClient webClient;

    @Before
    public void setUp() throws Exception {
        webClient = new WebClientImpl(new HttpClientProvider());
    }

    @Test
    public void shouldReturnContentOoValidUrl() throws Exception {
        final String content = webClient.getContent("http://www.onet.pl");
    }

    @Test(expected = IllegalStateException.class)
    public void showThrowExceptionOnInvalidUrl() throws Exception {
        final String content = webClient.getContent("somethingwhichisnoturl");
    }
}