package com.coolpeng.framework.qtask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by luanhaipeng on 16/8/1.
 */
public class QueueTaskRunner {

    private ExecutorService threadPool = null;

    public QueueTaskRunner() {
        threadPool = Executors.newSingleThreadExecutor();
    }

    public QueueTaskRunner(int threadCount) {
        if(threadCount<=1){
            threadPool = Executors.newSingleThreadExecutor();
        }else {
            threadPool = Executors.newFixedThreadPool(threadCount);
        }
    }

    public void addTask(final QueueTask task) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    task.runTask();
                }catch (Throwable e){
                }
            }
        });
    }


}
