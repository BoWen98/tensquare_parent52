package com.tensquare.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: tensquare
 * @author: Li Wenwen
 * @create: 2019-01-24 13:19
 */
@Data
@Configuration
@ConfigurationProperties("ly.sms")
public class SmsProperties {

    int appid;
    String appkey;
    String smsSign;
    int templateId;
}
