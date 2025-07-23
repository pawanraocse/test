package com.practise.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Map<String, String> map = Collections.emptyMap();
        map.put("key", "value");

        System.out.println("test failing");


        /* Test test = new Test();
        final Optional<Object> propertyValueFromConfig = test.getPropertyValueFromConfig("null");
        System.out.println(propertyValueFromConfig.isPresent());
        System.out.println(propertyValueFromConfig.get() + "");

        Queue<String> queue = new LinkedList<>();
        queue.add("as");
        queue.remove();*/

//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        final Future<Integer> future = executorService.submit(new Task());
//
//        CompletableFuture<String> completableFuture
//            = CompletableFuture.supplyAsync(() -> "Hello")
//            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
//        try {
//            final Integer response = future.get();
//            System.out.println(response);
//
//            System.out.println("Function Completed");
//            executorService.shutdown();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }

        // int[] nums = {2,9,3,8,4,6,5};
        // System.out.println(findKthLargest(nums, 4));
       /* Map<Integer, Set<String>> id2Values = new HashMap<>();
        id2Values.put(1, Set.of("one", "two", "three"));
        id2Values.put(2, Set.of("one1", "two3", "three four"));
        id2Values.put(3, Set.of("one2", "two3", "three four"));
        id2Values.put(4, Set.of("one3", "two3", "three four"));
        final String s = convertToString(id2Values);
        System.out.println(s);*/

      /*  List<Per> perList = new ArrayList<>();
        perList.add(new Per());
        perList.add(new Per());
        perList.add(new Per());
        perList.add(new Per());
        perList.add(new Per());

        final List<Per> collect = perList.stream().peek(p -> p.name = "bb wines").collect(Collectors.toList());

        collect.forEach(p -> {
            System.out.println(p.name);
        });*/

        /*Test test = new Test();
        test.runStream();*/

        printPageNumberAndSize(0,2);
        printPageNumberAndSize(4,2);

    }

    private static void printPageNumberAndSize(int startIndex, int limit) {
        int pageNumber = 0;
        int pageSize = 0;

        if (startIndex > 0) {
            pageNumber = startIndex / limit;
            pageSize = startIndex % limit;
        }
        System.out.println("---------------------------------");
        System.out.println("PageSize: " + pageSize);
        System.out.println("PageNumber: " + pageNumber);
    }

    private void runStream() {
        int batchSize = 10;

        LazyExample lazyExample = new LazyExample();
        final Stream<Map<Integer, Integer>> lazyStream = lazyExample.getLazyStream();

        Iterator<Map<Integer, Integer>> iterator = lazyStream.iterator();
        while (iterator.hasNext()) {
            final Map<Integer, Integer> collectedData = iterator.next();
            System.out.println(collectedData.size() + "---" + collectedData.keySet().iterator().next());
        }
    }

    private List<Map<Integer, Integer>> getNextBatch(Iterator<Map<Integer, Integer>> iterator, int batchSize) {
        List<Map<Integer, Integer>> batch = new ArrayList<>();
        for (int i = 0; i < batchSize && iterator.hasNext(); i++) {
            batch.add(iterator.next());
        }
        return batch;
    }

    private static class LazyExample {

        public Stream<Map<Integer, Integer>> getLazyStream() {
            int limit = 100;
            List<Integer> integerList = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                integerList.add(i);
            }
            return integerList.stream().map(id -> {
                Map<Integer, Integer> map = new HashMap<>();
                map.put(id, id);
                return map;
            });
        }
    }

    private static class Per {

        String name = "Default";
    }

    public static String convertToString(Map<Integer, Set<String>> id2Values) {
        return id2Values.entrySet().stream()
            .map(entry -> entry.getKey() + "," + String.join("|", entry.getValue()))
            .collect(Collectors.joining("\n"));
    }

    private Optional<Object> getPropertyValueFromConfig(String key) {
        try {
            return Optional.ofNullable(getConfigByKey(key).get(key));
        } catch (IOException e) {
            System.out.println("exception is caught");
            return Optional.empty();
        }
    }

    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        Arrays.stream(nums).forEach(queue::add);

        while (k > 1) {
            int a = queue.poll();
            System.out.println(a);
            k--;
        }
        return queue.isEmpty() ? 0 : queue.peek();
    }

    private Map<String, String> getConfigByKey(final String key) throws IOException {
        if (Objects.equals(null, key)) {
            throw new IOException();
        }
        Map<String, String> map = new HashMap<>();
        map.put(key, key);
        return map;
    }

    private static void LastOrder(Task val) {
        System.out.println(val);
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws InterruptedException {
            System.out.println("Executing call function");
            return new Random().nextInt();
        }
    }
}


