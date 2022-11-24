package org.programs.kdt;

public class Volatile {
    private static volatile boolean stopRequested;
    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            Integer i = 0;
            while (!stopRequested) {
                    i++;
            }
        });
        backgroundThread.start();
        Thread.sleep(1000);
        stopRequested = true;
    }
}