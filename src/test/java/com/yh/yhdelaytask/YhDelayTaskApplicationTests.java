package com.yh.yhdelaytask;

import com.yh.yhdelaytask.model.TimeTriggerMsg;
import com.yh.yhdelaytask.service.TimeTrigger;
import com.yh.yhdelaytask.service.impl.AirDelayQueue;
import com.yh.yhdelaytask.service.impl.MessageDelayQueue;
import com.yh.yhdelaytask.service.impl.ParkingDelayQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YhDelayTaskApplicationTests {

    @Autowired
    private AirDelayQueue airDelayQueue;
    @Autowired
    private MessageDelayQueue messageDelayQueue;
    @Autowired
    private ParkingDelayQueue parkingDelayQueue;

    @Test
    void contextLoads() {
        TimeTriggerMsg timeTriggerMsg = new TimeTriggerMsg();
        timeTriggerMsg.setTriggerTime(System.currentTimeMillis()+1000);
        timeTriggerMsg.setParam("空调执行器");
        timeTriggerMsg.setSpId(2L);
        airDelayQueue.add(timeTriggerMsg);
        timeTriggerMsg.setTriggerTime(System.currentTimeMillis()+5000);
        timeTriggerMsg.setParam("消息执行器");
        timeTriggerMsg.setSpId(2L);
        messageDelayQueue.add(timeTriggerMsg);
        timeTriggerMsg.setTriggerTime(System.currentTimeMillis()+10000);
        timeTriggerMsg.setParam("停车场执行器");
        timeTriggerMsg.setSpId(2L);
        parkingDelayQueue.add(timeTriggerMsg);
    }

}
