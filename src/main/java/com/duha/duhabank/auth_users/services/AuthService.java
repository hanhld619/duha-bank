package com.duha.duhabank.auth_users.services;

import com.duha.duhabank.auth_users.dtos.LoginRequest;
import com.duha.duhabank.auth_users.dtos.LoginResponse;
import com.duha.duhabank.auth_users.dtos.RegistrationRequest;
import com.duha.duhabank.res.Response;
import com.duha.duhabank.auth_users.dtos.ResetPasswordRequest;

public interface AuthService {
	
	Response<String> register(RegistrationRequest request);
	Response<LoginResponse> login(LoginRequest request);
	Response<?> forgetPassword(String email);
	Response<? > updatePasswordViaResetCode(ResetPasswordRequest resetPasswordRequest);
}
