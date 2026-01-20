package com.duha.duhabank.auth_users.services.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.duha.duhabank.account.entity.Account;
import com.duha.duhabank.account.repo.AccountRepo;
import com.duha.duhabank.account.services.AccountService;
import com.duha.duhabank.auth_users.dtos.LoginRequest;
import com.duha.duhabank.auth_users.dtos.LoginResponse;
import com.duha.duhabank.auth_users.dtos.RegistrationRequest;
import com.duha.duhabank.auth_users.dtos.ResetPasswordRequest;
import com.duha.duhabank.auth_users.entity.User;
import com.duha.duhabank.auth_users.repo.PasswordResetCodeRepo;
import com.duha.duhabank.auth_users.repo.UserRepo;
import com.duha.duhabank.auth_users.services.AuthService;
import com.duha.duhabank.enums.AccountType;
import com.duha.duhabank.enums.Currency;
import com.duha.duhabank.exceptions.BadRequestException;
import com.duha.duhabank.exceptions.NotFoundException;
import com.duha.duhabank.notification.dtos.NotificationDTO;
import com.duha.duhabank.notification.services.NotificationService;
import com.duha.duhabank.res.Response;
import com.duha.duhabank.role.entity.Role;
import com.duha.duhabank.role.repo.RoleRepo;
import com.duha.duhabank.security.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final NotificationService notificationService;
    private final AccountService accountService;

    private final PasswordResetCodeRepo passwordResetCodeRepo;
	
	@Override
	public Response<String> register(RegistrationRequest req) {
		List<Role> roles;
		
		if(req.getRoles() == null || req.getRoles().isEmpty()) {
			//DEFAUL CUSTOMER
			Role defaultRole = roleRepo.findByName("CUSTOMER").orElseThrow(() -> new NotFoundException("CUSTOMER ROLE NOT FOUND!"));
			
			roles = Collections.singletonList(defaultRole);
		} else {
			roles = req.getRoles().stream()
					.map(roleName -> roleRepo.findByName(roleName)
					.orElseThrow(() -> new NotFoundException(roleName + " ROLE NOT FOUND"))).toList();
		}
		
		if(userRepo.findByEmail(req.getEmail()).isPresent()) {
			throw new BadRequestException("Email Already Present!");
		}
		
		User user = User.builder()
				.firstName(req.getFirstName())
				.lastName(req.getLastName())
				.email(req.getEmail())
				.phoneNumber(req.getPhoneNumber())
				.password(passwordEncoder.encode(req.getPassword()))
				.roles(roles)
				.active(true)
				.build();
		
		User savedUser = userRepo.save(user);
		
		//Create ACCOUNT NUMBERT FOR THE USER
		Account savedAccount = accountService.createAccount(AccountType.SAVING, savedUser);
		
		//SEND WELCOME EMAIL
		Map<String, Object> vars = new HashMap<>();
		vars.put("name", savedUser.getFirstName());
		
		NotificationDTO notification = NotificationDTO.builder()
				.recipient(savedUser.getEmail())
				.subject("Welcome to Duha Bank 🎉")
				.templateName("welcome")
				.templateVariables(vars)
				.build();
		
		notificationService.sendEmail(notification, savedUser);
		
		Map<String, Object> accountVars = new HashMap<>();
		accountVars.put("name", savedUser.getFirstName());
        accountVars.put("accountNumber", savedAccount.getAccountNumber());
        accountVars.put("accountType", AccountType.SAVING.name());
        accountVars.put("currency", Currency.USD);
		
        NotificationDTO accountCreatedEmail = NotificationDTO.builder()
                .recipient(savedUser.getEmail())
                .subject("Your New Bank Account Has Been Created ✅")
                .templateName("account-created")
                .templateVariables(accountVars)
                .build();
        
        notificationService.sendEmail(accountCreatedEmail, savedUser);
        
		return Response.<String>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Your account has been created successfully")
				.data("Email of your account details has been sent to you. Your account number is: \" + savedAccount.getAccountNumber()")
				.build();
	}

	@Override
	public Response<LoginResponse> login(LoginRequest req) {
		
		String email = req.getEmail();
		String password = req.getPassword();
		
		User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found!"));
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadRequestException("Password doesn't match");
		}
		
		String token = tokenService.generateToken(user.getEmail());
		
		LoginResponse loginResponse = LoginResponse.builder()
				.roles(user.getRoles().stream().map(Role::getName).toList())
				.token(token)
				.build();
		
		return Response.<LoginResponse>builder()
				.statusCode(HttpStatus.OK.value())
				.data(loginResponse)
				.build();
	}

	@Override
	public Response<?> forgetPassword(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<?> updatePasswordViaResetCode(ResetPasswordRequest resetPasswordRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
