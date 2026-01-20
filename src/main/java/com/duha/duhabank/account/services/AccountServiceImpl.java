package com.duha.duhabank.account.services;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.duha.duhabank.account.dtos.AccountDTO;
import com.duha.duhabank.account.entity.Account;
import com.duha.duhabank.account.repo.AccountRepo;
import com.duha.duhabank.auth_users.entity.User;
import com.duha.duhabank.enums.AccountType;
import com.duha.duhabank.res.Response;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService{
	
	private final AccountRepo accountRepo;
	
	private final Random random = new Random();

	@Override
	public Account createAccount(AccountType accountType, User user) {
		log.info("Insdie createAccount()");
		String accountNumber = generateAccountNumber();
		return null;
	}

	@Override
	public Response<List<AccountDTO>> getMyAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<?> closeAccount(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String generateAccountNumber() {
		String accountNumber;
		do {
			// Generate a random 8-digit number (from 10,000,000 to 99,999,999)
            // and combine it with the "66" prefix.
			accountNumber = "66" + (random.nextInt(90000000) + 10000000);
		} while (accountRepo.findByAccountNumber(accountNumber).isPresent());
		
		log.info("account number generated {}", accountRepo);
        return accountNumber;
	}

}
