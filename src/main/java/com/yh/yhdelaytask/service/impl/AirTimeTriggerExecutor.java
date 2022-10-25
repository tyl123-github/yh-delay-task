package com.yh.yhdelaytask.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yh.yhdelaytask.model.TimeTriggerMsg;
import com.yh.yhdelaytask.service.TimeTriggerExecutor;
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
public class AirTimeTriggerExecutor implements TimeTriggerExecutor {

    @Override
    public void execute(TimeTriggerMsg timeTriggerMsg) {
        log.info("空调控制执行器执行任务{}", timeTriggerMsg);
    }
}
