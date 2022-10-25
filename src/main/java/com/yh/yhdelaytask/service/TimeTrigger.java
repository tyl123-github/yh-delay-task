package com.yh.yhdelaytask.service;


import com.yh.yhdelaytask.model.TimeTriggerMsg;

/**
 * 功能描述:  延时执行接口
 *
 * @author yl_tao
 * @date 2022/8/16 13:20
 */
public interface TimeTrigger {


    /**
     * 添加延时任务
     *
     * @param timeTriggerMsg 延时任务信息
     */
    void add(TimeTriggerMsg timeTriggerMsg);

}
