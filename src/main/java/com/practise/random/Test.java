package com.practise.random;

public class Test {

    public static void main(String[] args) {
        int aiApplicationFlowSize = 1090;

        long lockTimeout = aiApplicationFlowSize > 25 ? 25 + (aiApplicationFlowSize / 5)
            : 25;
        System.out.println(lockTimeout);

        /*intTest intTest = new intTest();
       intTest.log("Testing log");*/
    }

    @FunctionalInterface
    public interface Interface2 {

        void method2();

        default void log(String str) {
            System.out.println("I2 logging::" + str);
        }
    }

    @FunctionalInterface
    public interface Interface1 {

        void method1(String str);

        default void log(String str) {
            System.out.println("I1 logging::" + str);
        }

        static void print(String str) {
            System.out.println("Printing " + str);
        }
    }

    static class intTest implements Interface1, Interface2 {

        @Override
        public void method2() {

        }

        @Override
        public void method1(final String str) {

        }

        @Override
        public void log(final String str) {
            Interface2.super.log(str);
        }
    }

}


