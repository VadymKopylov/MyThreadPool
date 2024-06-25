package main.java.com.kopylov;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

public class MyFixedThreadPool {

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final ArrayList<Thread> workers = new ArrayList<>();
    private volatile boolean isShutdown = false;

    public MyFixedThreadPool(int threadsCount) {
        for (int i = 0; i < threadsCount; i++) {
            Thread worker = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = queue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) {
        if (!isShutdown) {
            queue.offer(task);
        } else {
            throw new RejectedExecutionException("ThreadPool has been shut down");
        }
    }

    public void shutdown() {
        isShutdown = true;
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }
}
