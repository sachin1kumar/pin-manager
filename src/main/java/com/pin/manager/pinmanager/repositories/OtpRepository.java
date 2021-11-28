package com.pin.manager.pinmanager.repositories;

import com.pin.manager.pinmanager.entities.OtpDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpDetail, String> {

    OtpDetail findByMsisdnOrOtp(String msisdn, String otp);

    @Modifying
    @Query("update OtpDetail otpDetail set otpDetail.validationCounter = :validationCounter where otpDetail.msisdn = :msisdn")
    int updateAttemptsForVerification(@Param("validationCounter") int validationCounter, @Param("msisdn") String msisdn);
}
