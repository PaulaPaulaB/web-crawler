package com.wawelska.webcrawler;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlFinder {

    private static final String URL_REGEXP = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
    private static final Pattern PATTERN = Pattern.compile(URL_REGEXP);

    public List<String> findUrls(String content) {
        List<String> urls = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(
                Objects.requireNonNull(content, "Content is null")
        );
        while (matcher.find()) {
            urls.add((content.substring(matcher.start(0),
                    matcher.end(0))));
        }
        return urls;
    }
}
