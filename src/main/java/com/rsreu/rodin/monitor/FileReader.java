package com.rsreu.rodin.monitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class FileReader implements Runnable {


    private static final Character SEARCH_SYMBOL = 'a';

    private static final Integer COUNT_REPETITIONS_SYMBOL = 10;

    private final String path;
    private final AtomicInteger symbolCounter;

    public FileReader(String path, AtomicInteger atomicCounter) {
        this.path = path;
        this.symbolCounter = atomicCounter;
    }

    @Override
    public void run() {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            List<Character> characters = lines.flatMap(s ->
                            s.chars().mapToObj(i -> (char) i))
                    .toList();
            for (Character character : characters) {
                if (character.equals(SEARCH_SYMBOL)) {
                    synchronized (symbolCounter) {
                        int count = symbolCounter.incrementAndGet();
                        if (count >= COUNT_REPETITIONS_SYMBOL) {
                            return;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
