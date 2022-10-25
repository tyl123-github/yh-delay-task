package com.yh.yhdelaytask.service;


import com.yh.yhdelaytask.model.TimeTriggerMsg;

/**
 * 功能描述: 延时任务执行器接口
 *
 * @author yl_tao
 * @date 2022/8/16 13:20
 */
public interface TimeTriggerExecutor {


    /**
     * 执行任务
     *
     * @param timeTriggerMsg 任务参数
     */
    void execute(TimeTriggerMsg timeTriggerMsg);

}
