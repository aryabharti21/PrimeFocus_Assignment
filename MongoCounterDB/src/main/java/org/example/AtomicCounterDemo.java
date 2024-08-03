package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounterDemo {
    private static final int NUM_THREADS = 10;
    private static final int ITERATIONS_PER_THREAD = 100;

    public static void main(String[] args) {
        MongoDBConnections mongoDBConnection = new MongoDBConnections();
        AtomicCounter counter = new AtomicCounter(mongoDBConnection.getCollection(), "demo_counter");
        AtomicLong totalOperations = new AtomicLong(0);

        System.out.println("Starting AtomicCounterDemo...");

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < ITERATIONS_PER_THREAD; j++) {
                        long sequence = counter.getNextSequence();
                        System.out.println(Thread.currentThread().getName() + " got sequence: " + sequence);
                        totalOperations.incrementAndGet();
                    }
                } catch (Exception e) {
                    System.err.println("Error in thread " + Thread.currentThread().getName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("ExecutorService was interrupted: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("AtomicCounterDemo completed.");
        System.out.println("Total operations performed: " + totalOperations.get());

        mongoDBConnection.close();
    }
}