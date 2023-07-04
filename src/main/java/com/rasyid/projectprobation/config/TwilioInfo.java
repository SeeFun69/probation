package com.rasyid.projectprobation.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("twilio")
public class TwilioInfo {
    private String ACCOUNT_SID;
    private String AUTH_TOKEN;
    private String PHONE_NUMBER;
}
