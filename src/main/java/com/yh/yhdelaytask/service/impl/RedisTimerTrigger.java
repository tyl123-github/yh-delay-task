package com.yh.yhdelaytask.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yh.yhdelaytask.model.TimeTriggerMsg;
import com.yh.yhdelaytask.service.TimeTrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述: redis 延时任务
 *
 * @author yl_tao
 * @date 2022/8/16 13:22
 */
@Service
@Slf4j
public class RedisTimerTrigger implements TimeTrigger {

    @Autowired
    private MessageDelayQueue testDelayQueue;

    @Override
    public void add(TimeTriggerMsg timeTriggerMsg) {
        //计算延迟时间 执行时间-当前时间
        Integer delaySecond = Math.toIntExact(timeTriggerMsg.getTriggerTime() - System.currentTimeMillis());
        //设置延时任务
        if (testDelayQueue.addJob(JSONUtil.toJsonStr(timeTriggerMsg), delaySecond)) {
            log.info("定时执行在【" + DateUtil.date(timeTriggerMsg.getTriggerTime()) + "】，消费【" + timeTriggerMsg.getParam().toString() + "】");
        } else {
            log.error("延时任务添加失败:{}", timeTriggerMsg);
        }
    }
}
