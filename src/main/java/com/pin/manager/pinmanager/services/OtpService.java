package com.pin.manager.pinmanager.services;

import com.pin.manager.pinmanager.entities.Otp;
import com.pin.manager.pinmanager.entities.OtpDetail;
import com.pin.manager.pinmanager.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

import static com.pin.manager.pinmanager.utils.Constants.*;

//Here, OTP represents PIN as per requirement.
@Service
@Transactional
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
        final String numbers = NUMBERS;
        final Random random = new Random();
        final StringBuilder otp = new StringBuilder();

        for (int index = 0; index < OTP_LENGTH; index++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    public Otp verifyMsisdn(Otp otp, String msisdn) {
        final String passedOtp = otp.getOtp();
        final Otp response = new Otp();
        final OtpDetail otpDetail = otpRepository.findByMsisdnOrOtp(msisdn, passedOtp);
        response.setOtp(passedOtp);
        if (otpDetail == null) {
            response.setMessage("Incorrect MSISDN & OTP!");
            return response;
        }
        int validationCounter = otpDetail.getValidationCounter();
        if (validationCounter == 0) {
            response.setMessage("You have exceeded your max limit!");
            return response;
        }
        if (otpDetail.isValidated()) {
            response.setMessage("User is already verified!");
            return response;
        }
        if (otpDetail.getOtp().equalsIgnoreCase(passedOtp) && otpDetail.getMsisdn().equalsIgnoreCase(msisdn)
                && !otpDetail.isValidated()) {
            response.setMessage("User verified!");
            otpDetail.setValidated(true);
        } else if (otpDetail.getOtp().equalsIgnoreCase(passedOtp) && !otpDetail.getMsisdn().equalsIgnoreCase(msisdn)) {
            response.setMessage("Incorrect MSISDN!");
        } else {
            response.setMessage("Incorrect OTP!");
            if (otpRepository.updateAttemptsForVerification(validationCounter - 1, msisdn) < 1) {
                response.setMessage("Error while updating number of attempts!");
            }
        }
        return response;
    }
}
