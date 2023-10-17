package dev.ronse.redalert;

import dev.ronse.redalert.orefalerts.OrefAlertNotifier;

import java.util.concurrent.TimeUnit;

public class TestRun {

    public static void main(String[] args) throws InterruptedException {
        OrefAlertNotifier notifier = new OrefAlertNotifier.Builder()
                .listener(System.out::println)
                .onException(e -> e.printStackTrace())
                .build();

        notifier.listen();

        TimeUnit.SECONDS.sleep(60);
    }

}
