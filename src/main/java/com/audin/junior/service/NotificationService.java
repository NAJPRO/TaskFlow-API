package com.audin.junior.service;

import com.audin.junior.entity.OtpValidation;

public interface NotificationService {
    public void sendOtp(OtpValidation validation);

    public void welcomeUser(OtpValidation validation);
}
