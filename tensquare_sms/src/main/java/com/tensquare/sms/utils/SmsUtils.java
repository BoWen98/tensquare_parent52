package com.tensquare.sms.utils;


import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.tensquare.sms.config.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

/**
 * @program: tensquare
 * @author: Li Wenwen
 * @create: 2019-01-24 13:25
 */

@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SmsProperties smsProperties;
    public SmsMultiSenderResult sendSms(String[] phoneNumbers, String[] params) {
        try {

            SmsMultiSender msender = new SmsMultiSender(smsProperties.getAppid(), smsProperties.getAppkey());
            SmsMultiSenderResult result = msender
                    .sendWithParam("86",
                            phoneNumbers,
                            smsProperties.getTemplateId(),
                            params,
                            smsProperties.getSmsSign(),
                            "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        } catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
        }
        return null;
    }

}
