package com.founderz.daeketalk;

import com.founderz.daeketalk.sms.SmsProperties;
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
public class DaekeTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaekeTalkApplication.class, args);
    }

}
