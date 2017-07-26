package com.wawelska.webcrawler;

import java.util.Set;
import java.util.concurrent.BlockingQueue;


public class Worker implements Runnable {

    final private WebClient webClient;
    final private BlockingQueue<String> queue;
    final private Set<String> visitedUrls;
    final private UrlFinder urlFinder;
    final private ContentAnalyzer contentAnalyzer;

    public Worker(
            WebClient client,
            BlockingQueue<String> queue,
            Set<String> visitedUrls,
            UrlFinder urlFinder,
            ContentAnalyzer contentAnalyzer
    ) {
        this.webClient = client;
        this.queue = queue;
        this.visitedUrls = visitedUrls;
        this.urlFinder = urlFinder;
        this.contentAnalyzer = contentAnalyzer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String content;
            String currentUrl;
            try {
                currentUrl = queue.take();
                if (visitedUrls.contains(currentUrl)) {
                    continue;
                }
                content = webClient.getContent(currentUrl);
                contentAnalyzer.analyzeOccurencies(content);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }
            visitedUrls.add(currentUrl);
            for (String url : urlFinder.findUrls(content)) {
                if (visitedUrls.contains(url)) {
                    continue;
                } else {
                    queue.offer(url);
                }
            }
        }
    }
}
