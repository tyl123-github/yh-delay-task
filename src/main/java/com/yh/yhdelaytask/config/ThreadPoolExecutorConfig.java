package com.yh.yhdelaytask.config;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author yl_tao
 * @date 2022/10/25 13:20
 */
@Component
public class ThreadPoolExecutorConfig {

    private static ThreadPoolExecutor threadPoolExecutor = null;

    public synchronized ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor != null) {
            return threadPoolExecutor;
        }
        //执行线程
        return new ThreadPoolExecutor(
                4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
                r -> {
                    Thread thread = new Thread(r);
                    //线程名称即当前队列名称
                    thread.setName("custom");
                    return thread;
                });
    }

}
