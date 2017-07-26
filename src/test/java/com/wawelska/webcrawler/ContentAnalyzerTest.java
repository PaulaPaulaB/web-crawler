package com.wawelska.webcrawler;


import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class ContentAnalyzerTest {

    @Test
    public void shouldReturnProperlyAmountOfSearchedWords() {

        String content = "wróbel zwyczajny[4], wróbel domowy, wróbel [5] " +
                "(Passer domesticus) – gatunek małego ptaka osiadłego ";

        Map<String, AtomicInteger> searchedWords = new HashMap<>();

        final String firstWord = "wróbel";
        searchedWords.put(firstWord, new AtomicInteger(0));
        final String secondWord = "mazurek";
        searchedWords.put(secondWord, new AtomicInteger(0));
        final String thirdWord = "gatunek";
        searchedWords.put(thirdWord, new AtomicInteger(0));

        ContentAnalyzer contentAnalyzer = new ContentAnalyzer(searchedWords);
        contentAnalyzer.analyzeOccurencies(content);

        assertEquals(3, searchedWords.get(firstWord).intValue());
        assertEquals(1, searchedWords.get(thirdWord).intValue());
        assertEquals(0, searchedWords.get(secondWord).intValue());
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionOnNullInput() {
        Map<String, AtomicInteger> searchedWords = new HashMap<>();
        ContentAnalyzer contentAnalyzer = new ContentAnalyzer(searchedWords);
        contentAnalyzer.analyzeOccurencies(null);
    }

    @Test
    public void shoulTryAnalizeWhenInputIsEmpty() {
        String content = "";
        Map<String, AtomicInteger> searchedWords = new HashMap<>();
        final String word = "wróbel";
        searchedWords.put(word, new AtomicInteger(0));
        ContentAnalyzer contentAnalyzer = new ContentAnalyzer(searchedWords);
        contentAnalyzer.analyzeOccurencies(content);

        assertEquals(0, searchedWords.get(word).intValue());
    }

}

