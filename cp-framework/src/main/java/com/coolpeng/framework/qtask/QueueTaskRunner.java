package com.coolpeng.framework.qtask;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by luanhaipeng on 16/8/1.
 */
public class QueueTaskRunner {

    private LinkedBlockingQueue<QueueTask> taskQueue =null;

    private boolean isRunning = false;


    public QueueTaskRunner() {
        this.taskQueue = new LinkedBlockingQueue<>(1000);
    }

    public QueueTaskRunner(int num) {
        this.taskQueue = new LinkedBlockingQueue<>(num);
    }

    public void addTask(QueueTask task){
        startTaskRunner();
        try {
            taskQueue.put(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTaskRunner(){

        if (isRunning){
            return;
        }

        isRunning = true;


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    try {
                        QueueTask task = taskQueue.poll();
                        if (task!=null){
                            try {
                                task.runTask();
                            }catch (Throwable e){
                                e.printStackTrace();
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();

    }



}
