package com.pin.manager.pinmanager.services;

import com.pin.manager.pinmanager.entities.Otp;
import com.pin.manager.pinmanager.entities.OtpDetail;
import com.pin.manager.pinmanager.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

import static com.pin.manager.pinmanager.utils.Constants.*;

//Here, OTP represents PIN as per requirement.
@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(String msisdn) {
        final OtpDetail otpDetail = new OtpDetail(msisdn, getOTP()
                , new Date(), false, VALIDATION_COUNTER);
        otpRepository.save(otpDetail);
        return otpDetail.getOtp();
    }

    private String getOTP() {
        String numbers = NUMBERS;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int index = 0; index < OTP_LENGTH; index++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    public Otp verifyMsisdn(Otp otp, String msisdn) {
        final String passedOtp = otp.getOtp();
        final Otp response = new Otp();
        final OtpDetail otpDetail = otpRepository.findByMsisdnAndOtp(msisdn, passedOtp);
        response.setOtp(passedOtp);
        if (otpDetail != null && otpDetail.getOtp().equalsIgnoreCase(passedOtp) &&
            otpDetail.getMsisdn().equalsIgnoreCase(msisdn)) {
            response.setMessage("User verified");
        } else {
            response.setMessage("Invalid User");
        }
        return response;
    }
}
