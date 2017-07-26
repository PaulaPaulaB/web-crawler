package com.wawelska.webcrawler;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static boolean DEBUG = false;

    public static void main(String[] args) throws Exception {

        final WebClient webClient = new WebClientImpl(new HttpClientProvider());
        final UrlFinder urlFinder = new UrlFinder();
        final BlockingQueue<String> queue = new LinkedBlockingQueue<>(1000);
        final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<String>());
        final Map<String, AtomicInteger> occurrenceStatistics;

        String startingUrl = "http://www.wp.pl";
        queue.add(startingUrl);

        List<String> wordsToCheck = new ArrayList<>();
        wordsToCheck.add("Lewandowski");
        wordsToCheck.add("PiS");
        wordsToCheck.add("PO");
        wordsToCheck.add("Kaczyński");
        wordsToCheck.add("Chodakowska");
        wordsToCheck.add("zdrowie");

        occurrenceStatistics = wordsToCheck
                .stream()
                .collect(Collectors.toMap(word -> word, word -> new AtomicInteger(0)));

        final ContentAnalyzer contentAnalyzer = new ContentAnalyzer(occurrenceStatistics);

        final Worker task = new Worker(webClient, queue, visitedUrls, urlFinder, contentAnalyzer);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }

        Thread.sleep(10000);

        threads.forEach(Thread::interrupt);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("statystyki:");
        occurrenceStatistics.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        });

    }

}
