package com.pin.manager.pinmanager.controllers;

import com.pin.manager.pinmanager.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/verify/{msisdn}")
    public String generateOtp(@PathVariable String msisdn) {
        return otpService.generateOtp(msisdn);
    }
}
