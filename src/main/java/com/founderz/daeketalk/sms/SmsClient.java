package com.founderz.daeketalk.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "xquare-client", url = "https://apis.ssodaa.com")
public interface SmsClient {

    @PostMapping("/sms/send/sms")
    void xquareUser(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody SendSmsRequest request,
            @RequestHeader("x-forwarded-for") String forwardedFor
    );
}
