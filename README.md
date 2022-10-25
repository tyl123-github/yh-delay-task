# yh-delay-task
## springboot v2.6.13 + redis   延时任务执行器

1. 延时任务具体执行方法
```java
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
```
