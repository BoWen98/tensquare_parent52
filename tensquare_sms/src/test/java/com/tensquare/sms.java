package com.tensquare;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @program: tensquare
 * @author: Li Wenwen
 * @create: 2019-01-24 15:17
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class sms {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend(){
        HashMap<String, String[]> msg = new HashMap<>();
        msg.put("phoneNumbers", new String[]{"15776591633"});
        msg.put("code", new String[]{"5432"});
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",msg);
    }
}
