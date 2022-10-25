package com.yh.yhdelaytask.service.impl;

import cn.hutool.json.JSONUtil;
import com.yh.yhdelaytask.model.TimeTriggerMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author yl_tao
 * @date 2022/9/8 10:02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AirDelayQueue extends AbstractDelayQueueMachineFactory {

    private final AirTimeTriggerExecutor airTimeTriggerExecutor;

    @Override
    public void invoke(String jobId) {
        TimeTriggerMsg timeTriggerMsg = JSONUtil.toBean(jobId, TimeTriggerMsg.class);
        airTimeTriggerExecutor.execute(timeTriggerMsg);
    }

    @Override
    public String setDelayQueueName() {
        return "air_command_delay";
    }

}
