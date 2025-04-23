package com.founderz.daektalk.sms;

import com.founderz.daektalk.sms.dto.SendSmsForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sms", url = "https://apis.ssodaa.com")
public interface SmsClient {

    @RequestMapping(method = RequestMethod.POST, value = "/sms/send/sms")
    void sendSms(
            @RequestHeader("x-api-key") String apiKey,
            @RequestHeader("x-forwarded-for") String forwardedFor,
            @RequestBody SendSmsForm request
    );
}
