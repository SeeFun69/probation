package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.config.TwilioInfo;
import com.rasyid.projectprobation.dto.SmsRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SmsService {

    private final TwilioInfo twilioInfo;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(
                twilioInfo.getACCOUNT_SID(),
                twilioInfo.getAUTH_TOKEN()
        );

        System.out.println("Yes" + twilioInfo.getACCOUNT_SID()+ " " + twilioInfo.getAUTH_TOKEN());
    }

    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {


            MessageCreator creator = Message.creator(
                    new PhoneNumber(smsRequest.getPhoneNumber())
                    , new PhoneNumber(twilioInfo.getPHONE_NUMBER())
                    , smsRequest.getMessage());

            creator.create();

            System.out.println("Messes Sent" + creator);
        }
        else
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {

        return true;
    }
}
