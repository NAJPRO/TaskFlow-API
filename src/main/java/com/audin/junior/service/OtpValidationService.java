package com.audin.junior.service;

import com.audin.junior.entity.OtpValidation;
import com.audin.junior.entity.User;

public interface OtpValidationService {
    public void save(User user);
    public OtpValidation showValidation(User user);
    public void confirmOtp(OtpValidation otpValidation);

}
