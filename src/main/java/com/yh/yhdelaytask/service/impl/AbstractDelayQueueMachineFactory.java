package com.yh.yhdelaytask.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yh.yhdelaytask.config.ThreadPoolExecutorConfig;
import com.yh.yhdelaytask.model.TimeTriggerMsg;
import com.yh.yhdelaytask.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 延时队列工厂
 *
 * @author yl_tao
 * @date 2022/8/16 13:22
 */
@Slf4j
public abstract class AbstractDelayQueueMachineFactory {


    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ThreadPoolExecutorConfig threadPoolExecutorConfig;

    /**
     * 功能描述: redis插入任务id
     *
     * @param jobId 任务id(队列内唯一)
     * @param time  延时时间(单位 :毫秒秒)
     * @return 是否插入成功
     * @author yl_tao
     * @date 2022/9/8 10:28
     */
    public boolean addJob(String jobId, Integer time) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND, time);
        long delaySeconds = instance.getTimeInMillis();
        boolean result = redisUtil.zAdd(setDelayQueueName(), delaySeconds, jobId);
        log.info("增加延时任务, 缓存key {}, 等待时间 {}毫秒", setDelayQueueName(), time);
        return result;

    }

    /**
     * 功能描述: 新增任务
     *
     * @param timeTriggerMsg 任务参数
     * @author yl_tao
     * @date 2022/9/8 10:27
     */
    public void add(TimeTriggerMsg timeTriggerMsg) {
        //计算延迟时间 执行时间-当前时间
        Integer delaySecond = Math.toIntExact(timeTriggerMsg.getTriggerTime() - System.currentTimeMillis());
        //设置延时任务
        if (this.addJob(JSONUtil.toJsonStr(timeTriggerMsg), delaySecond)) {
            log.info("定时执行在【" + DateUtil.date(timeTriggerMsg.getTriggerTime()) + "】，消费【" + timeTriggerMsg.getParam() + "】");
        } else {
            log.error("延时任务添加失败:{}", timeTriggerMsg);
        }
    }

    /**
     * 功能描述: 延时队列机器开始运作
     *
     * @author yl_tao
     * @date 2022/9/8 10:26
     */
    private void startDelayQueueMachine() {
        log.info("延时队列机器{}开始运作", setDelayQueueName());
        //监听redis队列
        while (true) {
            try {
                //获取当前时间的时间戳
                long now = System.currentTimeMillis();
                //redis获取当前时间之前的任务
                Set<ZSetOperations.TypedTuple<Object>> tuples = redisUtil.zRangeByScore(setDelayQueueName(), 0, now);
                //如果任务不为空
                if (!CollectionUtils.isEmpty(tuples)) {
                    log.info("延时任务开始执行任务:{}", JSONUtil.toJsonStr(tuples));
                    for (ZSetOperations.TypedTuple<Object> tuple : tuples) {
                        String jobId = (String) tuple.getValue();
                        // 移除缓存，如果移除成功则表示当前线程处理了延时任务，则执行延时任务
                        Long num = redisUtil.zRemove(setDelayQueueName(), jobId);
                        // 如果移除成功, 则执行
                        if (num > 0) {
                            invoke(jobId);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("处理延时任务发生异常,异常原因为{}", e.getMessage(), e);
            } finally {
                // 间隔5秒钟搞一次
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 功能描述: 最终执行的任务方法
     *
     * @param jobId 任务id
     * @author yl_tao
     * @date 2022/9/8 10:26
     */
    public abstract void invoke(String jobId);


    /**
     * 功能描述: 要实现延时队列的名字
     *
     * @return 返回当前队列名称
     * @author yl_tao
     * @date 2022/9/8 10:25
     */
    public abstract String setDelayQueueName();


    @PostConstruct
    public void init() {
        threadPoolExecutorConfig.getThreadPoolExecutor().execute(this::startDelayQueueMachine);
    }

}
