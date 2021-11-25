package com.pin.manager.pinmanager.services;

import com.pin.manager.pinmanager.entities.OtpDetail;
import com.pin.manager.pinmanager.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static com.pin.manager.pinmanager.utils.Constants.*;

//Here, OTP represents PIN as per requirement.
@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(String msisdn) {
        String otp = Arrays.toString(getOTP());
        System.out.println("Otp:"+otp);
        final OtpDetail otpDetail = new OtpDetail(msisdn, otp
                , new Date(), false, VALIDATION_COUNTER);
        otpRepository.save(otpDetail);
        return otpDetail.getOtp();
    }

    private char[] getOTP() {
        String numbers = NUMBERS;
        Random random = new Random();
        char[] otp = new char[OTP_LENGTH];

        for (int index = 0; index < OTP_LENGTH; index++) {
            otp[index] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }
}
