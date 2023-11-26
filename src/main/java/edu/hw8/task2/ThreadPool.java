package edu.hw8.task2;

public interface ThreadPool extends AutoCloseable {
    static ThreadPool create(int threadCount) {
        return new FixedThreadPool(threadCount);
    }

    @Override
    void close();

    void start();

    void execute(Runnable runnable);
}
