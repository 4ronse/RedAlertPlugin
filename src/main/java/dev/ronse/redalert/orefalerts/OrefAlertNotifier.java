package dev.ronse.redalert.orefalerts;

import dev.ronse.redalert.Requester;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class OrefAlertNotifier {
    private final Set<OrefAlertListener> listeners;
    private final int delay;
    private final Consumer<Exception> exceptionConsumer;

    private boolean stop = false;

    private OrefAlert testAlert = null;

    private OrefAlertNotifier(int delay, Set<OrefAlertListener> listeners, Consumer<Exception> exceptionConsumer) {
        this.listeners = listeners;
        this.delay = delay;
        this.exceptionConsumer = exceptionConsumer;
    }

    public CompletableFuture<Void> listen() {
        return CompletableFuture.runAsync(() -> {
            OrefAlert lastAlert = null;

            try {
                while (!stop) {
                    try {
                        TimeUnit.SECONDS.sleep(this.delay);

                        boolean isTest = testAlert != null;
                        OrefAlert alert = !isTest ? Requester.checkForAlerts() : testAlert;
                        if(testAlert != null) testAlert = null;

                        if (alert == null) continue;
                        if (alert.equals(lastAlert)) continue;

                        lastAlert = isTest ? lastAlert : alert;

                        this.listeners.forEach(l -> l.onOrefAlert(alert));
                    } catch (Exception e) {
                        this.exceptionConsumer.accept(e);
                    }
                }
            }  finally {
                this.listeners.clear();
            }
        });
    }

    public void test(OrefAlert alert) {
        testAlert = alert;
    }

    public void stop() {
        stop = true;
    }

    public static class Builder {
        private int delay = 1;
        private Consumer<Exception> exceptionConsumer = (ex) -> {};

        private final Set<OrefAlertListener> listeners = new HashSet<>();

        public Builder() {}

        public Builder every(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder listener(OrefAlertListener l) {
            listeners.add(l);
            return this;
        }

        public Builder onException(Consumer<Exception> ec) {
            this.exceptionConsumer = ec;
            return this;
        }

        public OrefAlertNotifier build() {
            return new OrefAlertNotifier(delay, listeners, exceptionConsumer);
        }
    }
}
