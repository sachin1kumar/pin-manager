package com.pin.manager.pinmanager.entities;

public class Otp {

   private String otp;
   private String message;

   public Otp() {

   }

   public Otp(String otp, String message) {
       this.otp = otp;
       this.message = message;
   }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
