package com.yh.yhdelaytask.service.impl;

import com.yh.yhdelaytask.model.TimeTriggerMsg;
import com.yh.yhdelaytask.service.TimeTriggerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能描述: test执行器
 *
 * @author yl_tao
 * @date 2022/8/16 13:21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageTimeTriggerExecutor implements TimeTriggerExecutor {


    @Override
    public void execute(TimeTriggerMsg timeTriggerMsg) {
        log.info("执行器具执行任务{}", timeTriggerMsg);
    }
}
