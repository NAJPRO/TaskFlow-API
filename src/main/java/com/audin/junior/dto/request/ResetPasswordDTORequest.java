package com.audin.junior.dto.request;

public record ResetPasswordDTORequest(String email, String newPassword, String confirmNewPassword, String otp) {

}
