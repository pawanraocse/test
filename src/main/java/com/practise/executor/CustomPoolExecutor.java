package com.practise.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPoolExecutor {

    BlockingQueue<Runnable> queue;
    Worker[] workers;

    AtomicBoolean shutdown;
    int capacity;

    CustomPoolExecutor(int n) {
        workers = new Worker[n];
        queue = new LinkedBlockingQueue<>();
        shutdown = new AtomicBoolean();
        capacity = n;

        for (int i = 0; i < n; i++) {
            workers[i] = new Worker();
            workers[i].setName("Thread-" + i);
            workers[i].start();
        }
    }

    public void setShutdown() {
        shutdown.set(true);
        for (int i = 0; i < capacity; i++) {
            queue.add(() -> System.out.println("Shutting down thread"));
        }
    }

    public void submit(Runnable task) {
        if (shutdown.get()) {
            return;
        }
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class Worker extends Thread {
        @Override
        public void run() {
            while (!shutdown.get()) {
                try {
                    queue.take().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CustomPoolExecutor customPoolExecutor = new CustomPoolExecutor(3);
        for (int i = 0; i < 10; i++) {
            customPoolExecutor.submit(() -> {
                System.out.println("running task on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread.sleep(5000);
        customPoolExecutor.setShutdown();
    }
}
