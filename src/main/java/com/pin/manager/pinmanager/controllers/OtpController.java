package com.pin.manager.pinmanager.controllers;

import com.pin.manager.pinmanager.entities.Otp;
import com.pin.manager.pinmanager.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/{msisdn}")
    public String generateOtp(@PathVariable String msisdn) {
        return otpService.generateOtp(msisdn);
    }

    @PostMapping("/{msisdn}")
    public ResponseEntity<Otp> verifyMsisdn(@RequestBody Otp otp, @PathVariable String msisdn) {
       Otp response = otpService.verifyMsisdn(otp, msisdn);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
