package com.wawelska.webcrawler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ContentAnalyzer {

    public static final String SEPARATOR_REGEXP = "[\\.:,;!\\p{javaSpaceChar}]";
    private final Map<String, AtomicInteger> occurances;

    public ContentAnalyzer(Map<String, AtomicInteger> occurrenceStatistics) {
        this.occurances = occurrenceStatistics;
    }

    public void analyzeOccurencies(String content) {
        String[] contentAsArray = content.split(SEPARATOR_REGEXP);
        for (String word : contentAsArray) {
            if (occurances.containsKey(word)) {
                occurances.get(word).incrementAndGet();
            }
        }
    }
}
