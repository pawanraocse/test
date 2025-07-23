package com.practise.random;

public class JavaSemaphore {

    static class Holder {
        static final JavaSemaphore instance = new JavaSemaphore();
    }

    public static JavaSemaphore getInstance() {
        return Holder.instance;
    }

    public static void main(String[] args) {

    }


}
