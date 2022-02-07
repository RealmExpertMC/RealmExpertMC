package br.com.realmexpert.common.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class RealmExpertMCThreadBox {

    public static final ScheduledExecutorService METRICS = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Metrics"));

    public static final ExecutorService ASYNCCHAT = Executors.newCachedThreadPool(new NamedThreadFactory("Async Chat Thread"));

    public static final ExecutorService FILEIO = Executors.newFixedThreadPool(2, new NamedThreadFactory("RealmExpertMC File IO Thread"));

    public static final ExecutorService ASYNCEXECUTOR = Executors.newSingleThreadExecutor(new NamedThreadFactory("RealmExpertMC Async Task Handler Thread"));

    public static final ExecutorService TCW = Executors.newSingleThreadExecutor(new NamedThreadFactory("TerminalConsoleWriter"));

    public static final ExecutorService Head = Executors.newFixedThreadPool(3, new NamedThreadFactory("Head Conversion Thread"));

    public static ScheduledThreadPoolExecutor WatchRealmExpertMC = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("WatchRealmExpertMC"));

    public static class AssignableThread extends Thread {
        public AssignableThread(Runnable run) {
            super(run);
        }

        public AssignableThread() {
            super();
        }
    }
}
