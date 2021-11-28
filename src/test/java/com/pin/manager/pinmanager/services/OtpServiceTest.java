package com.pin.manager.pinmanager.services;

import com.pin.manager.pinmanager.entities.Otp;
import com.pin.manager.pinmanager.entities.OtpDetail;
import com.pin.manager.pinmanager.repositories.OtpRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OtpServiceTest {
    @InjectMocks
    private OtpService otpService;

    @Mock
    private OtpRepository otpRepository;

    @Test
    public void should_generate_otp_of_length_four() {
        String otp = otpService.generateOtp("+34999112236");
        assertEquals(4, otp.length());
    }

    @Test
    public void should_return_incorrect_msisdn_and_otp_when_order_detail_is_null() {
        final Otp otp = new Otp();
        otp.setOtp("1234");
        when(otpRepository.findByMsisdnOrOtp("+34999112236", otp.getOtp())).thenReturn(null);
        final Otp actualOtp = otpService.verifyMsisdn(otp, "+34999112236");
        assertEquals("Incorrect MSISDN & OTP!", actualOtp.getMessage());
    }

    @Test
    public void should_return_exceed_limit_message_when_validation_count_is_zero() {
        final Otp otp = new Otp();
        otp.setOtp("1234");
        final OtpDetail otpDetail = new OtpDetail();
        otpDetail.setValidationCounter(0);
        when(otpRepository.findByMsisdnOrOtp("+34999112236", otp.getOtp())).thenReturn(otpDetail);
        final Otp actualOtp = otpService.verifyMsisdn(otp, "+34999112236");
        assertEquals("You have exceeded your max limit!", actualOtp.getMessage());
    }

    @Test
    public void should_return_user_already_verified_message_when_otp_validated_flag_is_true() {
        final Otp otp = new Otp();
        otp.setOtp("1234");
        final OtpDetail otpDetail = new OtpDetail();
        otpDetail.setValidationCounter(3);
        otpDetail.setValidated(true);
        when(otpRepository.findByMsisdnOrOtp("+34999112236", otp.getOtp())).thenReturn(otpDetail);
        final Otp actualOtp = otpService.verifyMsisdn(otp, "+34999112236");
        assertEquals("User is already verified!", actualOtp.getMessage());
    }

    @Test
    public void should_return_user_verified_message_when_otp_validated_flag_is_false() {
        final Otp otp = new Otp();
        otp.setOtp("1234");
        final OtpDetail otpDetail = new OtpDetail();
        otpDetail.setValidationCounter(3);
        otpDetail.setMsisdn("+34999112236");
        otpDetail.setOtp("1234");
        otpDetail.setValidated(false);
        when(otpRepository.findByMsisdnOrOtp(otpDetail.getMsisdn(), otp.getOtp())).thenReturn(otpDetail);
        final Otp actualOtp = otpService.verifyMsisdn(otp, otpDetail.getMsisdn());
        assertEquals("User verified!", actualOtp.getMessage());
    }

    @Test
    public void should_return_incorrect_otp_message_when_otp_is_different() {
        final Otp otp = new Otp();
        otp.setOtp("1235");
        final OtpDetail otpDetail = new OtpDetail();
        otpDetail.setValidationCounter(3);
        otpDetail.setMsisdn("+34999112237");
        otpDetail.setOtp("1234");
        otpDetail.setValidated(false);
        when(otpRepository.findByMsisdnOrOtp("+34999112237", otp.getOtp())).thenReturn(otpDetail);
        when(otpRepository.updateAttemptsForVerification(otpDetail.getValidationCounter() - 1,
                "+34999112237")).thenReturn(1);
        final Otp actualOtp = otpService.verifyMsisdn(otp, "+34999112237");
        assertEquals("Incorrect OTP!", actualOtp.getMessage());
    }

    @Test
    public void should_return_update_number_of_attempts_message_when_updation_error_occurs() {
        final Otp otp = new Otp();
        otp.setOtp("1235");
        final OtpDetail otpDetail = new OtpDetail();
        otpDetail.setValidationCounter(3);
        otpDetail.setMsisdn("+34999112237");
        otpDetail.setOtp("1234");
        otpDetail.setValidated(false);
        when(otpRepository.findByMsisdnOrOtp("+34999112237", otp.getOtp())).thenReturn(otpDetail);
        when(otpRepository.updateAttemptsForVerification(otpDetail.getValidationCounter() - 1,
                "+34999112237")).thenReturn(0);
        final Otp actualOtp = otpService.verifyMsisdn(otp, "+34999112237");
        assertEquals("Error while updating number of attempts!", actualOtp.getMessage());
    }
}
