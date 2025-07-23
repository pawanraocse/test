package com.practise;

public class ExceptionTest {

    public static void main(String[] args) throws InterruptedException {
        int i = 5;
        ExceptionTest exceptionTest = new ExceptionTest();
        try {
            Thread commandThread = new Thread(() -> exceptionTest.listOptions(i));
            commandThread.start();

            while (commandThread.isAlive()) {
                Thread.sleep(1000); // Sleep for some time before checking again
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("Completed!!");

    }

    public void listOptions(int value) {
        if (value == 5) {
            System.exit(1);
        }
        System.out.println("value " + value);
    }
}


