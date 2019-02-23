package com.tensquare.sms.mq;

import com.tensquare.sms.config.SmsProperties;
import com.tensquare.sms.utils.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Map;


@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "sms.verify.code.queue", durable = "true"),
            exchange = @Exchange(value = "ly.sms.exchange", type = ExchangeTypes.TOPIC),
            key = "sms.verify.code"
    ))
    public void listenSms(Map<String, String[]> msg) {
        String[] phoneNumbers = msg.remove("phoneNumbers");
        String[] codes = msg.get("code");
        System.out.println(codes);

        if (CollectionUtils.isEmpty(Arrays.asList(phoneNumbers))) {
            //不做处理
            return;
        }
        //发送消息
        smsUtils.sendSms(phoneNumbers, codes);

        log.info("[短信服务],发送短信验证码,手机号{}" + phoneNumbers);
    }
}
