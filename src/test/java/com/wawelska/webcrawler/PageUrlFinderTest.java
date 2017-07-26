package com.wawelska.webcrawler;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageUrlFinderTest {

    private final UrlFinder urlFinder = new UrlFinder();

    @Test
    public void shouldReturnOneUrl() {
        String html = "<a href=\"www.wp.pl\">";
        assertEquals("www.wp.pl", urlFinder.findUrls(html).get(0));
        assertEquals(1, urlFinder.findUrls(html).size());
    }

    @Test(expected = NullPointerException.class)
    public void nullAsURLShouldReturnException() {
        urlFinder.findUrls(null);
    }

    @Test
    public void shouldReturnTwoUrls() {
        String html = "<a href=\"www.wp.pl\"> nananana a href=\"www.interia.pl\">  eee makarena";
        assertEquals(2, urlFinder.findUrls(html).size());
    }


    @Test
    public void shouldReturnOneUrlOnHttpProtocol() {
        String html = "http://www.wp.pl ";
        assertEquals("www.wp.pl", urlFinder.findUrls(html).get(0));
        assertEquals(1, urlFinder.findUrls(html).size());
    }

    @Test
    public void shouldReturTwonUrlsWithoutWWW() {
        String html = "https://wp.pl  http://wp.pl ";
        assertEquals("wp.pl", urlFinder.findUrls(html).get(0));
        assertEquals(2, urlFinder.findUrls(html).size());
    }

    @Test
    public void shouldReturnOneUrlWithoutWWWandHttp() {
        String html = "wp.pl ";
        final List<String> urls = urlFinder.findUrls(html);
        assertEquals("wp.pl", urls.get(0));
        assertEquals(1, urls.size());
    }

    @Test
    public void shouldReturnEmptyList() {
        String html = "nanana eeeee makarena ";
        assertEquals(0, urlFinder.findUrls(html).size());
    }

}
