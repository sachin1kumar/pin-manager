package com.pin.manager.pinmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "otp_detail")
public class OtpDetail {

    @Id
    private String msisdn;

    private String otp;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "is_validated")
    private boolean isValidated;

    @Column(name = "validation_counter")
    private int validationCounter;

    public OtpDetail() {

    }

    public OtpDetail(String msisdn, String otp, Date creationDate, boolean isValidated, int validationCounter) {
        this.msisdn = msisdn;
        this.otp = otp;
        this.creationDate = creationDate;
        this.isValidated = isValidated;
        this.validationCounter = validationCounter;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public int getValidationCounter() {
        return validationCounter;
    }

    public void setValidationCounter(int validationCounter) {
        this.validationCounter = validationCounter;
    }
}
