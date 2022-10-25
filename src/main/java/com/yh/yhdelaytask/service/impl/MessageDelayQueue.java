package com.yh.yhdelaytask.service.impl;

import cn.hutool.json.JSONUtil;
import com.yh.yhdelaytask.model.TimeTriggerMsg;
import com.yh.yhdelaytask.service.TimeTrigger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述: test执行器
 *
 * @author yl_tao
 * @date 2022/8/16 13:21
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageDelayQueue extends AbstractDelayQueueMachineFactory implements TimeTrigger {

    @Autowired
    private MessageTimeTriggerExecutor messageTimeTriggerExecutor;

    @Override
    public void invoke(String jobId) {
        TimeTriggerMsg timeTriggerMsg = JSONUtil.toBean(jobId, TimeTriggerMsg.class);
        messageTimeTriggerExecutor.execute(timeTriggerMsg);
    }

    @Override
    public String setDelayQueueName() {
        return "message_delay";
    }

}