package com.yh.yhdelaytask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述: 延时任务消息
 *
 * @author yl_tao
 * @date 2022/8/16 13:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTriggerMsg implements Serializable {


    private static final long serialVersionUID = -1L;

    /**
     * 执行器 执行时间  时间戳 毫秒
     */
    private Long triggerTime;

    /**
     * 当前服务商id
     */
    private Long spId;

    /**
     * 执行器参数
     */
    private String param;


}
