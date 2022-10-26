package com.rsreu.rodin.monitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileReader implements Runnable {

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
                if (character.equals('a')) {
                    synchronized (symbolCounter) {
                        int count = symbolCounter.incrementAndGet();
                        if (count >= 10) {
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
