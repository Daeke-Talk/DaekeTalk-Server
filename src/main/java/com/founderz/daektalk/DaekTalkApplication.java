package com.founderz.daektalk;

import com.founderz.daektalk.sms.SmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(SmsProperties.class)
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class DaekTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaekTalkApplication.class, args);
    }

}
